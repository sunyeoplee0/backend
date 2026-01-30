package com.fileinnout.domain.drive;

import com.fileinnout.global.BaseResponse;
import com.fileinnout.global.Controller;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class FileController implements Controller {
    // 서비스 클래스의 객체를 의존성 주입
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public BaseResponse process(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("ImageController 실행");
        try {
            //service를 통해 주소가 반환됨
            String result = fileService.upload(req);
            return BaseResponse.success(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
