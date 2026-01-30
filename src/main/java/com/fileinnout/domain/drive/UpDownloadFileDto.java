package com.fileinnout.domain.drive;

public class UpDownloadFileDto {

    public record UploadRequest(
            String fileName,
            String contentType,
            long fileSize
    ) {
    }

    public record UploadResponse(
            String fileId,
            String uploadUrl,
            String uploadMethod,
            String downloadUrl
    ) {
    }

    public record DownloadFileResponse(
            String fileId,
            String fileName,
            long fileSize,
            String contentType,
            String downloadUrl,
            String createdAt) {
    }

    public record FileListCheck(
            String fileId,
            String fileName,
            String contentType,
            long fileSize,
            String status,
            String createAt) {
    }
}