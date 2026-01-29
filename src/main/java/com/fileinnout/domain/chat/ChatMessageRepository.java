package com.fileinnout.domain.chat;

import com.fileinnout.domain.chat.model.ChatMessageDto;
import com.fileinnout.domain.chat.model.ChatRoomDto;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageRepository {
    private final DataSource ds;

    public ChatMessageRepository(DataSource ds) {
        this.ds = ds;
    }

    public ChatMessageDto.ChatMessageSendRes send(ChatMessageDto.ChatMessageSendReq req, Long senderId, String senderNickname) {
        String sql = "INSERT INTO chatmessage (roomID, senderID, content, type, createdAt) VALUES (?, ?, ?, ?, NOW())";

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO chatmessage (roomID, senderID, content, type, createdAt) VALUES (?, ?, ?, ?, NOW())",
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setLong(1, req.roomId());
            pstmt.setLong(2, senderId);
            pstmt.setString(3, req.content());
            pstmt.setString(4, String.valueOf(req.type()));

            int affectedRows = pstmt.executeUpdate(); // 💡 executeUpdate() 호출 필수!

            if (affectedRows == 0) {
                throw new SQLException("메시지 전송 실패: 영향을 받은 행이 없습니다.");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long messageId = rs.getLong(1); // 💡 roomId가 아니라 messageID입니다.
                    LocalDateTime now = LocalDateTime.now();

                    return new ChatMessageDto.ChatMessageSendRes(
                            messageId,
                            senderNickname, // 💡 응답에는 보낸 사람의 닉네임이 들어가야 합니다.
                            req.content(),
                            now
                    );
                } else {
                    throw new SQLException("메시지 저장 실패: 생성된 ID를 가져올 수 없습니다.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("메시지 저장 중 오류 발생", e);
        }
    }

    public List<ChatMessageDto.ChatMessageListRes> read(Long roomId){
        List<ChatMessageDto.ChatMessageListRes> messages = new ArrayList<>();
        String sql = "SELECT m.messageID, m.senderID, u.name as senderNickname, m.content, m.createdAt, m.type " +
                "FROM chatmessage m " +
                "JOIN user u ON m.senderID = u.idx " + // UserRepository 구조에 맞춤 (idx)
                "WHERE m.roomID = ? " +
                "ORDER BY m.createdAt ASC"; // 대화창이므로 과거순(오래된 순) 정렬

        try (Connection conn = ds.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, roomId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Timestamp ts = rs.getTimestamp("createdAt");
                    LocalDateTime createdAt = (ts != null) ? ts.toLocalDateTime() : null;

                    String typeStr = rs.getString("type");
                    MessageType typeEnum = MessageType.valueOf(typeStr);
                    messages.add(new ChatMessageDto.ChatMessageListRes(
                            rs.getLong("messageID"),
                            rs.getLong("senderID"),
                            rs.getString("senderNickname"),
                            rs.getString("content"),
                            createdAt,
                            typeEnum
                    ));
                }
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException("메시지 내역 조회 중 오류 발생", e);
        }
    }

}

