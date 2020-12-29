package com.revature.chronicle.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.when;

public class S3FileServiceTest {

    private S3Mock s3Mock;

    @Mock
    S3FileService s3FileService;

    @Mock
    private AmazonS3 s3ClientMock;

    @Mock
    private File file;

    @BeforeEach
    public void initEnv() {
        s3Mock = new S3Mock.Builder().withPort(8001).withInMemoryBackend().build();
        s3Mock.start();
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration("http://localhost:8001", "us-east-1");

        s3ClientMock = AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();

        s3FileService.setAwsClient(s3ClientMock);
        s3ClientMock.createBucket("test-bucket");
    }

    @Test
    public void uploadFileSuccessfullyAndReturnObjectURL() throws MalformedURLException {
        when(s3ClientMock.putObject("test-bucket", "test-key", file.getPath()));
        when(s3ClientMock.getUrl("test-bucket", file.getName()))
                .thenReturn(new URL("https://test-bucket.s3.amazonaws.com/" + file.getName()));
        String actualUrl = s3FileService.uploadFile(file);
        Assert.assertNotEquals(actualUrl, "");
        Assert.assertEquals(new URL("https://test-bucket.s3.amazonaws.com/" + file.getName()).toString(), actualUrl);
        s3Mock.shutdown();
    }

    @Test
    public void uploadFileFailWrongBucket() {
        when(s3ClientMock.putObject("wrong-bucket", "test-key", file.getPath()))
                .thenThrow(Mockito.mock(AmazonClientException.class));
        s3FileService.uploadFile(file);
        s3Mock.shutdown();
    }

    @Test
    public void getExistingObjectURL() throws MalformedURLException {
        when(s3ClientMock.getUrl("test-bucket", file.getName()))
                .thenReturn(new URL("https://test-bucket.s3.amazonaws.com/" + file.getName()));
        String actualUrl = s3FileService.getObjectUrl(file.getName());
        Assert.assertNotEquals(actualUrl, "");
        Assert.assertEquals(new URL("https://test-bucket.s3.amazonaws.com/" + file.getName()).toString(), actualUrl);
        s3Mock.shutdown();
    }
}
