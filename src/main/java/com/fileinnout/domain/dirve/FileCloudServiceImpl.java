package com.fileinnout.domain.dirve;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

public class FileCloudServiceImpl implements FileService {
    private final AwsCredentials credentials = AwsBasicCredentials.create(
            System.getenv("AWS_ACCESS_KEY"), System.getenv("AWS_SECRET_KEY")
    );
    private final S3Client s3Client = S3Client.builder()
            .region(Region.AP_NORTHEAST_2)
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build();

    @Override
    public String upload(HttpServletRequest req) throws IOException, ServletException {
        Part file = req.getPart("image");
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("be24-ss3")
                .key(file.getSubmittedFileName())
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        return "https://be24-s3.s3.ap-northeast-2.amazonaws.com/" + file.getSubmittedFileName();
    }
}
