package com.revature.chronicle.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.Test;
import org.junit.Assert;


import java.io.File;
import java.io.IOException;


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
        s3FileService.setAwsBucket("test-bucket");

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

        s3ClientMock.putObject("test-bucket", "temp", file);

        String actual = s3FileService.getObjectUrl("temp");
        String expected = s3ClientMock.getUrl("test-bucket", file.getName()).toString();

        Assert.assertEquals(expected, actual);
        s3Mock.stop();
    }
}
