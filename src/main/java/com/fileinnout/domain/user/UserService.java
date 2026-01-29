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
}
