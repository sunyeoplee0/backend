package com.fileinnout.domain.dirve;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.time.Duration;

public class ImagePreSignedServiceImpl implements ImageService{
    private final AwsCredentials credentials = AwsBasicCredentials.create(
        System.getenv("AWS_ACCESS_KEY"), System.getenv("AWS_SECRET_KEY")
    );
    private final S3Presigner s3Presigner = S3Presigner.builder()
            .region(Region.AP_NORTHEAST_2)
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build();

    @Override
    public String upload(HttpServletRequest req) throws IOException, ServletException {
        String filename = req.getParameter("filename");
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket("be24-s3")
                .key(filename)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(1))
                .putObjectRequest(putObjectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);
        String uploadUrl = presignedRequest.url().toString();

        return uploadUrl;
    }
}
