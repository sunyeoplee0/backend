package com.fileinnout.domain.dirve;

import jakarta.servlet.http.HttpServletRequest;

public interface ImageRepository {
    public void save(String submittedFileName);
    public String upload(HttpServletRequest req);
}
