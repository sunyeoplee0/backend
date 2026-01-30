package com.fileinnout.domain.drive;

import jakarta.servlet.http.HttpServletRequest;

public interface FileRepository {
    public void save(String submittedFileName);
    public String upload(HttpServletRequest req);
}
