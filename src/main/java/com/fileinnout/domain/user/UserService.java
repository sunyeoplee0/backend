package com.fileinnout.domain.user;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto.SignupRes register(UserDto.SignupReq signupRequest) {
        UserDto.SignupRes request = userRepository.register(signupRequest);
        return request;
    }

    public UserDto.LoginRes login(UserDto.LoginReq loginReq) {
        UserDto.LoginRes res = userRepository.login(loginReq);
        return res;
    }
}
