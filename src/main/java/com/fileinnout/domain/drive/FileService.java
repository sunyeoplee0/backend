package com.fileinnout.domain.drive;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface FileService {
    public String upload(HttpServletRequest req) throws IOException, ServletException;
}
