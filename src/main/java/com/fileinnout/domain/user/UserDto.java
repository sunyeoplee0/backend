package com.fileinnout.domain.user;

public class UserDto {

    public record SignupRequest(
            String name,
            String email,
            String password
    ) {}

    // 1. 회원가입 응답: 유저가 정상적으로 생성되었음을 알림
    public record SignupResponse(
            Long idx,
            String email,
            String name
    ) {}

    // 로그인 요청 DTO
    public record LoginRequest(
            String email,
            String password
    ) {}

    // 2. 로그인 응답: 서비스 이용을 위한 티켓(Token)을 발급함
    public record LoginResponse(
            String accessToken,
            String refreshToken,
            UserInfo user // 아래의 내부 record 사용
    ) {}

    // 로그인 응답에 포함될 간략한 유저 정보
    public record UserInfo(
            Long idx,
            String email,
            String name,
            String profileImage
    ) {}
}

