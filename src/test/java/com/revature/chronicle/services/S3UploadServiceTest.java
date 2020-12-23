package com.revature.chronicle.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;


public class S3UploadServiceTest {

    private S3Mock s3Mock;

    @Mock
    private AmazonS3 s3Client;

    @BeforeEach
    public void initEnv() {
        s3Mock = new S3Mock.Builder().withPort(8001).withInMemoryBackend().build();
        s3Mock.start();
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("http://localhost:8001", "us-east-1");

        s3Client = AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();

        s3Client.createBucket("testbucket");
    }

    @Test
    public void uploadMultipartVideoFileShouldReturn() {

    }

    @Test
    public void uploadMultipartNotesFile() {
        s3Mock.shutdown();
    }

}
