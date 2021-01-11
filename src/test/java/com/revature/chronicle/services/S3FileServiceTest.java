package com.revature.chronicle.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.Transfer;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class S3FileServiceTest {

    @Test
    public void uploadFileSuccessfullyAndReturnObjectURL() throws IOException, InterruptedException {

        S3FileService s3FileService = new S3FileService();
        File file = File.createTempFile("temp", ".txt");
        S3Mock s3Mock = new S3Mock.Builder().withPort(8001).withInMemoryBackend().build();
        s3Mock.start();
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("http://localhost:8001", "us-east-1");

        AmazonS3 s3ClientMock = AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();

        s3FileService.setAwsClient(s3ClientMock);
        s3ClientMock.createBucket("test-bucket");

        s3ClientMock.putObject("test-bucket", "temp", file);

        String result = s3FileService.uploadFile(file);
        String expected = s3ClientMock.getUrl("test-bucket", file.getName()).toString();

        Assert.assertEquals(expected, result);
        s3Mock.stop();
    }

    @Test
    public void getExistingObjectURL() throws IOException {
        S3FileService s3FileService = new S3FileService();
        File file = File.createTempFile("temp", ".txt");
        S3Mock s3Mock = new S3Mock.Builder().withPort(8002).withInMemoryBackend().build();
        s3Mock.start();
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("http://localhost:8002", "us-east-1");

        AmazonS3 s3ClientMock = AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();

        s3FileService.setAwsClient(s3ClientMock);
        s3ClientMock.createBucket("test-bucket");
        s3FileService.setAwsBucket("test-bucket");

        s3ClientMock.putObject("test-bucket", file.getName(), file);

        String actual = s3FileService.getObjectUrl(file.getName());
        String expected = s3ClientMock.getUrl("test-bucket", file.getName()).toString();

        Assert.assertEquals(expected, actual);
        s3Mock.stop();
    }

    @Test
    public void uploadVideoFileSuccessfully() throws IOException {
        S3FileService s3FileService = new S3FileService();
        File file = File.createTempFile("temp", ".mp4");
        long contentLength = file.length();
        long partSize = 25 * 1024 * 1024; // 25MB part size

        S3Mock s3Mock = new S3Mock.Builder().withPort(8003).withInMemoryBackend().build();
        s3Mock.start();
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("http://localhost:8003", "us-east-1");

        AmazonS3 s3ClientMock = AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();

        s3FileService.setAwsClient(s3ClientMock);
        s3ClientMock.createBucket("test-bucket");
        s3FileService.setAwsBucket("test-bucket");

        List<PartETag> partETags = new ArrayList<PartETag>();

        InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest("test-bucket", "temp");
        InitiateMultipartUploadResult initResponse = s3ClientMock.initiateMultipartUpload(initRequest);

        long filePosition = 0;
        for(int i = 1; filePosition < contentLength; i++) {
            partSize = Math.min(partSize, (contentLength-filePosition));

            UploadPartRequest uploadPartRequest = new UploadPartRequest()
                    .withBucketName("test-bucket")
                    .withKey("temp")
                    .withUploadId(initResponse.getUploadId())
                    .withPartNumber(i)
                    .withFileOffset(filePosition)
                    .withFile(file)
                    .withPartNumber((int) partSize);

            UploadPartResult uploadResult = s3ClientMock.uploadPart(uploadPartRequest);
            partETags.add(uploadResult.getPartETag());

            filePosition += partSize;
        }
        CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest("test-bucket", "temp",
                initResponse.getUploadId(), partETags);

        s3ClientMock.completeMultipartUpload(compRequest);

        String actual = s3FileService.uploadVideo(file);
        String expected = s3ClientMock.getUrl("test-bucket", file.getName()).toString();

        Assert.assertEquals(expected, actual);
        s3Mock.stop();
    }
}
