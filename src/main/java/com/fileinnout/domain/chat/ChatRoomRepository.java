package com.fileinnout.domain.chat;

import com.fileinnout.domain.chat.model.ChatRoomDto;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;


public class ChatRoomRepository {
    private final DataSource ds;

    public ChatRoomRepository(DataSource ds) {
        this.ds = ds;
    }


    public ChatRoomDto.ChatRoomCreateRes create(ChatRoomDto.ChatRoomCreateReq request) {
        try (
             Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO chatroom (title, created_at) VALUES (?, NOW())",
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, request.title());
            pstmt.executeUpdate();
            LocalDateTime now = LocalDateTime.now();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long roomId = rs.getLong(1);
                    return new ChatRoomDto.ChatRoomCreateRes(
                            roomId,
                            request.title(),
                            now
                    );
                } else {
                    throw new SQLException("채팅방 생성 실패: ID를 가져올 수 없습니다.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("채팅방 저장 중 오류 발생", e);
        }
    }

    // 2. 전체 채팅방 목록 조회 (최신 메시지 순)
    public List<ChatRoomDto.ChatRoomListRes> read(Long userIdx) {
        List<ChatRoomDto.ChatRoomListRes> rooms = new ArrayList<>();
        // 쿼리문의 컬럼명과 rs.get에서 쓰는 이름을 통일하세요!
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT roomID, title, lastMessage, lastSentAt FROM chatroom WHERE userIdx = ? ORDER BY lastSentAt DESC"
             )) { // 1. 여기서 객체만 생성

            pstmt.setLong(1, userIdx); // 2. 파라미터 셋팅은 밖에서 실행

            try (ResultSet rs = pstmt.executeQuery()) { // 3. ResultSet도 별도 try로 관리
                while (rs.next()) {
                    rooms.add(new ChatRoomDto.ChatRoomListRes(
                            rs.getLong("roomID"),
                            rs.getString("title"),
                            rs.getString("lastMessage"),
                            rs.getString("lastSentAt"),
                            0,
                            0
                    ));
                }
            }
            return rooms;
        } catch (SQLException e) {
            throw new RuntimeException("채팅방 목록 조회 중 오류 발생", e);
        }
    }
}