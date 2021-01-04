package com.revature.chronicle.services;

import com.amazonaws.services.s3.AmazonS3;

import java.io.File;

public class S3FileService {

    private AmazonS3 s3Client;


    public void setAwsClient(AmazonS3 s3Client) {
    }

    public String uploadFile(File file) {
        return "";
    }

    public String getObjectUrl(String name) {
        return "";
    }
}
