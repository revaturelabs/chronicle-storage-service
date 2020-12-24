package com.revature.chronicle.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectResult;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.mockito.Mock;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class S3FileServiceTest {

    private S3Mock s3Mock;

    @Mock
    S3FileService s3FileService;

    private AmazonS3 s3Client;

    @Mock
    private File file;

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

        s3FileService.setAwsClient(s3Client);
        s3Client.createBucket("test-bucket");
    }

    @Test
    public void uploadFileSuccessfullyAndReturnObjectURL() {
        doReturn(String.class).when(s3FileService).uploadFile(any(File.class));
        String url = s3FileService.uploadFile(file);
        Assert.assertNotEquals(url, "");
        Assert.assertTrue(url.contains("test-bucket"));
    }

    @Test
    public void getExistingObjectURL() {
        doReturn(String.class).when(s3FileService.getObjectUrl(file.getName()));
        String url = s3FileService.getObjectUrl(file.getName());
        Assert.assertNotEquals(url, "");
        Assert.assertTrue(url.contains("test-bucket"));
        Assert.assertTrue(url.contains(file.getName()));
    }
}
