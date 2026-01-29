package com.fileinnout.domain.Editor;

import org.mariadb.jdbc.Statement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditorRepository {
    private final DataSource ds;

    public EditorRepository(DataSource ds) {
        this.ds = ds;
    }

    public EditorDto.NewDocRes create(EditorDto.NewDocReq req) {
        try {
            Connection conn = ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO posts (id_idx, doc_type, group, permission) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            pstmt.setLong(1, req.id_idx());
            pstmt.setString(2, req.doc_type());
            if(req.group() == "private") {
                pstmt.setString(3, "private");
            }else {
                pstmt.setString(3, "shared");
//                for(Permission i : req.permission()) {
//                    pstmt.setString(4, (i.idx + ":" + i.permission));
//                }
            }
            if(pstmt.executeUpdate() == 0) {
                throw new SQLException("문서 생성 실패 : 업데이트가 안됐습니다.");
            }
            try (ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()) {
                    return new EditorDto.NewDocRes(
                            req.id_idx(),
                            rs.getLong(1),
                            req.group()
//                            req.permission()
                    );
                }else {
                    throw new SQLException("문제가 있는 칸이 있습니다.");
                }
            }catch (RuntimeException e) {
                throw new RuntimeException("rs가 문제있습니다.");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public EditorDto.EditDocRes edit(EditorDto.EditDocReq req) {
        try {
            Connection conn = ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM posts WHERE idx=(?) AND post_idx=(?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            pstmt.setLong(1, req.id_idx());
            pstmt.setLong(2, req.post_idx());
            if(pstmt.executeUpdate() == 0) {
                throw new SQLException("문서 편집에 실패했습니다!");
            }
            try (ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()) {
//                    if(rs.getObject(5) != null) {
//                        for(Permission i : rs.getObject(5).) {
//                            if(i.idx == req.id_idx()) {
//                                pstmt.setObject(3, req.permission());
//                                break;
//                            }
//                        }
//                    }
                    return new EditorDto.EditDocRes(
                            req.id_idx(),
                            rs.getLong(2),
                            rs.getString(3),
                            rs.getString(4)
//                            req.permission()
                    );
                }else {
                    throw new SQLException("문제가 있는 칸이 있습니다.");
                }
            }catch (RuntimeException e) {
                throw new RuntimeException("rs가 문제있습니다.");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public EditorDto.ViewDocRes view(EditorDto.ViewDocReq req) {
        try {
            Connection conn = ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT * FROM posts WHERE idx=(?) AND post_idx=(?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            pstmt.setLong(1, req.id_idx());
            pstmt.setLong(2, req.post_idx());

            if(pstmt.executeUpdate() == 0) {
                throw new SQLException("문서가 없습니다.");
            }
            try (ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()) {
                    return new EditorDto.ViewDocRes(
                            req.id_idx(),
                            req.post_idx(),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5)
                    );
                }else {
                    throw new SQLException("문제가 있는 칸이 있습니다.");
                }
            }catch (RuntimeException e) {
                throw new RuntimeException("rs가 문제있습니다.");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public EditorDto.DeleteDocRes delete(EditorDto.DeleteDocReq req) {
        try {
            Connection conn = ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM posts WHERE idx=(?) AND post_idx=(?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            pstmt.setLong(1, req.id_idx());
            pstmt.setLong(2, req.post_idx());
            if(pstmt.executeUpdate() == 0) {
                throw new SQLException("문서 삭제 실패 : 업데이트가 안됐습니다.");
            }
            try (ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()) {
                    return new EditorDto.DeleteDocRes(
                            req.id_idx(),
                            req.post_idx()
                    );
                }else {
                    throw new SQLException("문제가 있는 칸이 있습니다.");
                }
            }catch (RuntimeException e) {
                throw new RuntimeException("rs가 문제있습니다.");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public EditorDto.SaveDocRes save(EditorDto.SaveDocReq req) {
        try {
            Connection conn = ds.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO posts (id_idx, title, contents, doc_type) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            pstmt.setLong(1, req.id_idx());
            pstmt.setString(2, req.doc_type());
            pstmt.setString(3, req.title());
            pstmt.setString(4, req.contents());
            pstmt.setString(5, req.doc_type());
            if(pstmt.executeUpdate() == 0) {
                throw new SQLException("문서 저장 실패 : 업데이트가 안됐습니다.");
            }
            try (ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()) {
                    return new EditorDto.SaveDocRes(
                            req.id_idx(),
                            rs.getLong(1)
                    );
                }else {
                    throw new SQLException("문제가 있는 칸이 있습니다.");
                }
            }catch (RuntimeException e) {
                throw new RuntimeException("rs가 문제있습니다.");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
