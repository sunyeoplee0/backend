package com.fileinnout.domain.user;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto.SignupResponse register(UserDto.SignupRequest signupRequest) {
        UserDto.SignupResponse request = userRepository.register(signupRequest);
        return request;
    }
}
