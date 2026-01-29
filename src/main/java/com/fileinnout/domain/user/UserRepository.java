package com.fileinnout.domain.user;

import javax.sql.DataSource;
import java.sql.*;

public class UserRepository {
    private final DataSource ds;

    public UserRepository(DataSource ds) {
        this.ds = ds;
    }

    public UserDto.SignupRes register(UserDto.SignupReq signupRequest) {

        try {
            Connection conn = ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user (name, email, password) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, signupRequest.name());
            pstmt.setString(2, signupRequest.email());
            pstmt.setString(3, signupRequest.password());

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
}
