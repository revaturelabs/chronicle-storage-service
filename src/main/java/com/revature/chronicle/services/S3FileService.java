package com.revature.chronicle.services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URL;

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

        TransferManager tm = TransferManagerBuilder
                .standard()
                .withS3Client(this.s3Client)
                .build();

        Upload upload = tm.upload(new PutObjectRequest(this.awsBucket, file.getName(), file.getAbsolutePath()));
        upload.waitForCompletion();

        URL s3Url = s3Client.getUrl(this.awsBucket, file.getName());
        return s3Url.toString();
    }

    public String getObjectUrl(String name) {
        URL s3Url = this.s3Client.getUrl(this.awsBucket, name);
        return s3Url.toString();
    }
}
