package com.fileinnout.domain.user;

import com.fileinnout.global.BaseResponse;
import com.fileinnout.global.Controller;
import com.fileinnout.utils.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserController implements Controller {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public BaseResponse process(HttpServletRequest req, HttpServletResponse resp) {

        if(req.getRequestURI().contains("signup")) {
            UserDto.SignupReq signupReq = JsonParser.from(req, UserDto.SignupReq.class);
            UserDto.SignupRes request = userService.register(signupReq);
            return BaseResponse.success(request);
        } else if (req.getRequestURI().contains("login")) {
            UserDto.LoginReq loginReq = JsonParser.from(req, UserDto.LoginReq.class);
            UserDto.LoginRes res = userService.login(loginReq);
            return BaseResponse.success(res);
        }
        return null;
    }
}
