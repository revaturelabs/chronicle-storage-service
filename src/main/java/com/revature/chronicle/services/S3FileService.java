package com.revature.chronicle.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class S3FileService {

    private AmazonS3 s3Client;

    @Value("${aws.secret_access_key}")
    private String awsSecretKey;

    @Value("${aws.access_key_id}")
    private String awsAccessKey;

    @Value("${aws.s3.bucket}")
    private String awsBucket;

    @Value("${aws.s3.region}")
    private String awsRegion;

    @PostConstruct
    private void initializeAws() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(this.awsAccessKey, this.awsSecretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(this.awsRegion)
                .build();

        this.s3Client = s3Client;
    }

    public void setAwsBucket(String awsBucket) {
        this.awsBucket = awsBucket;
    }

    public void setAwsClient(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(File file) throws InterruptedException {
        try {
            TransferManager tm = TransferManagerBuilder
                    .standard()
                    .withS3Client(this.s3Client)
                    .build();


            Path path = Paths.get(file.getAbsolutePath());
            PutObjectRequest req = new PutObjectRequest(this.awsBucket, file.getName(), path.toFile()).withCannedAcl(CannedAccessControlList.PublicRead);

            Upload upload = tm.upload(req);
            UploadResult result = upload.waitForUploadResult();

        } catch (AmazonClientException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        URL s3Url = s3Client.getUrl(this.awsBucket, file.getName());
        System.out.println(s3Url);
        return s3Url.toString();
    }

    public String getObjectUrl(String name) {
        URL s3Url = this.s3Client.getUrl(this.awsBucket, name);
        return s3Url.toString();
    }

    public String uploadVideo(File file) {
        long contentLength = file.length();
        long partSize = 25 * 1024 * 1024; // 25MB part size
        String key = file.getName();

        try {
            List<PartETag> partETags = new ArrayList<PartETag>();
            InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(this.awsBucket, key).withCannedACL(CannedAccessControlList.PublicRead);
            InitiateMultipartUploadResult initResponse = s3Client.initiateMultipartUpload(initRequest);

            long filePosition = 0;
            for (int i = 1; filePosition < contentLength; i++) {


                partSize = Math.min(partSize, (contentLength - filePosition));

                // Create the request to upload a part.
                UploadPartRequest uploadRequest = new UploadPartRequest()
                        .withBucketName(this.awsBucket)
                        .withKey(key)
                        .withUploadId(initResponse.getUploadId())
                        .withPartNumber(i)
                        .withFileOffset(filePosition)
                        .withFile(file)
                        .withPartSize(partSize);

                // Upload the part and add the response's ETag to our list.
                UploadPartResult uploadResult = s3Client.uploadPart(uploadRequest);
                partETags.add(uploadResult.getPartETag());

                filePosition += partSize;
            }

            CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(this.awsBucket, key,
                    initResponse.getUploadId(), partETags);
            s3Client.completeMultipartUpload(compRequest);

        } catch (SdkClientException e) {
            e.printStackTrace();
        }

        URL s3Url = s3Client.getUrl(this.awsBucket, file.getName());
        System.out.println(s3Url);
        return s3Url.toString();
    }
}
