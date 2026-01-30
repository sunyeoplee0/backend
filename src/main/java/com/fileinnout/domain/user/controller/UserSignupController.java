package com.fileinnout.domain.user.controller;

import com.fileinnout.domain.user.UserDto;
import com.fileinnout.domain.user.UserService;
import com.fileinnout.global.BaseResponse;
import com.fileinnout.global.Controller;
import com.fileinnout.utils.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserSignupController implements Controller {
    private final UserService userService;

    public UserSignupController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public BaseResponse process(HttpServletRequest req, HttpServletResponse resp) {

            UserDto.SignupReq signupReq = JsonParser.from(req, UserDto.SignupReq.class);
            UserDto.SignupRes request = userService.register(signupReq);
            return BaseResponse.success(request);

    }
}
