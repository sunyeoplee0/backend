package com.fileinnout.global;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Controller {
    BaseResponse process(HttpServletRequest req, HttpServletResponse resp);
}
