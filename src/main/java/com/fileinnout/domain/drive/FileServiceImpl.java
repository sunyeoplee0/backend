package com.fileinnout.domain.drive;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.IOException;

public class FileServiceImpl implements FileService {
    // 레포지토리 클래스의 객체를 의존성 주입
    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public String upload(HttpServletRequest req) throws IOException, ServletException {
        Part file = req.getPart("image");
        file.write("c:\\uploads\\"+file.getSubmittedFileName());

        fileRepository.save(file.getSubmittedFileName());

        return file.getSubmittedFileName();
    }
}
