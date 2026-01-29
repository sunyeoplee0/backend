package com.fileinnout.domain.user;

import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.sql.DataSource;
import java.sql.*;

public class UserRepository {
    private final DataSource ds;

    public UserRepository(DataSource ds) {
        this.ds = ds;
    }

    public UserDto.SignupRes register(UserDto.SignupReq signupRequest, String hashedPassword) {

        try {
            Connection conn = ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user (name, email, password) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, signupRequest.name());
            pstmt.setString(2, signupRequest.email());
            pstmt.setString(3, hashedPassword);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("회원 등록 실패: 영향을 받은 행이 없습니다.");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long idx = rs.getLong(1);
                    return new UserDto.SignupRes(
                            idx,
                            signupRequest.email(),
                            signupRequest.name());
                } else {
                    throw new SQLException("회원가입 실패");
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDto.LoginRes login(UserDto.LoginReq loginReq) {
        String sql = "SELECT idx, email, password, name FROM user WHERE email = ?";

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 1. 이메일 파라미터만 바인딩
            pstmt.setString(1, loginReq.email());

            // 2. 조회 실행 (SELECT는 executeQuery 사용)
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // DB에서 가져온 비밀번호와 사용자가 입력한 비밀번호 비교
                    String dbPassword = rs.getString("password");

                    BCrypt.Result result = BCrypt.verifyer().verify(loginReq.password().toCharArray(), dbPassword);
                    if (result.verified) {
                        // 로그인 성공: 정보 반환
                        return new UserDto.LoginRes(
                                rs.getLong("idx"),
                                rs.getString("email"),
                                rs.getString("name"),
                                null
                        );
                    } else {
                        // 비밀번호 불일치
                        throw new RuntimeException("비밀번호가 일치하지 않습니다.");
                    }
                } else {
                    // 해당 이메일의 유저가 없음
                    throw new RuntimeException("존재하지 않는 사용자입니다.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("로그인 처리 중 DB 오류 발생", e);
        }
    }
}
