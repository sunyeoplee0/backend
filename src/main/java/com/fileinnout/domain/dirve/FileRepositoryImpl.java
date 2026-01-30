package com.fileinnout.domain.dirve;

import com.fileinnout.domain.dirve.UpDownloadFileDto;
import jakarta.servlet.http.HttpServletRequest;

import javax.sql.DataSource;
import java.sql.*;

public class FileRepositoryImpl implements FileRepository {
    private final DataSource ds;

    public FileRepositoryImpl(DataSource dataSource) {
        this.ds = dataSource;
    }

    public void save(String submittedFileName) {

        try {
            Class.forName("org.mariadb.jdbc.Driver");

            // DB 연결 객체를 다 사용하고 나면 반납할 수 있도록 수정
            try (Connection conn = ds.getConnection()) {
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO image (filename) VALUES (?)");
                pstmt.setInt(1, Integer.parseInt(submittedFileName));
                ResultSet rs = pstmt.executeQuery();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


//        try {
//            Class.forName("org.mariadb.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mariadb://10.10.10.30:3306/test", "root", "qwer1234");
//            try (Statement stmt = conn.createStatement()) {
//                Integer affectedRows = stmt.executeUpdate(
//                        "INSERT INTO image (filename) VALUES ('" + submittedFileName + "')");
//
//                if(affectedRows > 0) {
//                    System.out.println("업로드 성공");
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

    }

    @Override
    public String upload(HttpServletRequest req) {
        return "";
    }
}
