package com.fileinnout.domain.user.controller;

import com.fileinnout.domain.user.UserDto;
import com.fileinnout.domain.user.UserService;
import com.fileinnout.global.BaseResponse;
import com.fileinnout.global.Controller;
import com.fileinnout.utils.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserLoginController implements Controller {
    private final UserService userService;

    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public BaseResponse process(HttpServletRequest req, HttpServletResponse resp) {
        UserDto.LoginReq loginReq = JsonParser.from(req, UserDto.LoginReq.class);
        UserDto.LoginRes res = userService.login(loginReq);
        return BaseResponse.success(res);
    }
}
