package com.fileinnout.domain.dirve;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.IOException;

public class ImageServiceImpl implements ImageService {
    // 레포지토리 클래스의 객체를 의존성 주입
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public String upload(HttpServletRequest req) throws IOException, ServletException {
        Part file = req.getPart("image");
        file.write("c:\\uploads\\"+file.getSubmittedFileName());

        imageRepository.save(file.getSubmittedFileName());

        return file.getSubmittedFileName();
    }
}
