package com.fileinnout.domain.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.fileinnout.utils.JwtProvider;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto.SignupRes register(UserDto.SignupReq signupRequest) {
        String hashedPassword = BCrypt.withDefaults().hashToString(12, signupRequest.password().toCharArray());
        UserDto.SignupRes request = userRepository.register(signupRequest, hashedPassword);
        return request;
    }

    public UserDto.LoginRes login(UserDto.LoginReq loginReq) {
        UserDto.LoginRes res = userRepository.login(loginReq);
        String token = JwtProvider.createToken(res.idx(), res.name());
        return new UserDto.LoginRes(
                res.idx(),
                res.email(),
                res.name(),
                token
        );
    }
}
