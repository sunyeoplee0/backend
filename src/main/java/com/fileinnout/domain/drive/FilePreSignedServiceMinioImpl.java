package com.fileinnout.domain.drive;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;

public class FilePreSignedServiceMinioImpl implements FileService {
    private final AwsCredentials credentials =
            AwsBasicCredentials.create("minioadmin", "minioadmin");
    private final S3Presigner s3Presigner = S3Presigner.builder()
                    .region(Region.US_EAST_1)
                    .credentialsProvider(StaticCredentialsProvider.create(credentials))
                    .endpointOverride(URI.create("http://192.100.201.30:9000"))
                    .serviceConfiguration(
                            S3Configuration.builder()
                                    .pathStyleAccessEnabled(true)
                                    .build()
                    )
                    .build();

    @Override
    public String upload(HttpServletRequest req) throws IOException, ServletException {

        String filename = req.getParameter("filename");

        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("filename is required");
        }

        PutObjectRequest putObjectRequest =
                PutObjectRequest.builder()
                        .bucket("file-storage") // 미리 생성된 버킷
                        .key(filename)
                        .build();

        PutObjectPresignRequest presignRequest =
                PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(5)) // 5분 유효
                        .putObjectRequest(putObjectRequest)
                        .build();

        PresignedPutObjectRequest presignedRequest =
                s3Presigner.presignPutObject(presignRequest);

        return presignedRequest.url().toString();
    }
}
