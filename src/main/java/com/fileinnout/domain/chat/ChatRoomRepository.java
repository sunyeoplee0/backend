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
        String insertRoomSql = "INSERT INTO chatroom (title, createdAt) VALUES (?, NOW())";
        String insertParticipantSql = "INSERT INTO userChatroom (roomId, userIdx) VALUES (?, ?)";

        Connection conn = null;
        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작

            Long roomId = null;
            // 1. 채팅방 생성
            try (PreparedStatement pstmt = conn.prepareStatement(insertRoomSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, request.title());
                pstmt.executeUpdate();

                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        roomId = rs.getLong(1);
                    } else {
                        throw new SQLException("채팅방 생성 실패: ID를 가져올 수 없습니다.");
                    }
                }
            }

            // 2. 참여자 목록 저장 (Batch 처리)
            if (request.participantIdx() != null && !request.participantIdx().isEmpty()) {
                try (PreparedStatement pStmtPart = conn.prepareStatement(insertParticipantSql)) {
                    for (Long userIdx : request.participantIdx()) {
                        pStmtPart.setLong(1, roomId);
                        pStmtPart.setLong(2, userIdx);
                        pStmtPart.addBatch(); // 메모리에 명령을 쌓아둠
                    }
                    pStmtPart.executeBatch(); // 한 번에 DB로 전송
                }
            }

            conn.commit(); // 모든 SQL 실행 성공 시 확정
            return new ChatRoomDto.ChatRoomCreateRes(
                    roomId,
                    request.title(),
                    LocalDateTime.now()
            );

        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            throw new RuntimeException("채팅방 생성 중 오류 발생", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // 기본 상태로 복구
                    conn.close();
                } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    // 2. 전체 채팅방 목록 조회 (최신 메시지 순)
    public List<ChatRoomDto.ChatRoomListRes> read(Long userIdx) {
        List<ChatRoomDto.ChatRoomListRes> rooms = new ArrayList<>();
        // 쿼리문의 컬럼명과 rs.get에서 쓰는 이름을 통일하세요!
        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "SELECT r.roomID, r.title, r.lastMessage, r.lastSentAt " +
                             "FROM chatroom r " +
                             "JOIN userChatroom rm ON r.roomID = rm.roomID " +
                             "WHERE rm.userIDX = ? " +
                             "ORDER BY r.lastSentAt DESC"
             )) { // 1. 여기서 객체만 생성

            pstmt.setLong(1, userIdx); // 2. 파라미터 셋팅은 밖에서 실행

            try (ResultSet rs = pstmt.executeQuery()) { // 3. ResultSet도 별도 try로 관리
                while (rs.next()) {
                    rooms.add(new ChatRoomDto.ChatRoomListRes(
                            rs.getLong("roomId"),
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