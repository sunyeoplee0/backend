package com.fileinnout.domain.chat.model;

import com.fileinnout.domain.chat.MessageType;

import java.time.LocalDateTime;

public class ChatMessageDto {                   //채팅 메세지 전송 요청 DTO
    public record ChatMessageSendRequest(
            Long roomId,
            String content,
            MessageType type
    ) {}

    public record ChatMessageSendResponse(      //채팅 메세지 전송 응답 DTO
            Long messageId,
            String senderNickname,
            String content,
            LocalDateTime createdAt
    ) {}

    public record ChatMessageListRequest(       //채팅 메시지 내역 조회 요청 DTO
            Long roomId,
            Long lastMessageId, // No-offset 페이징용
            int size
    ) {}

    public record ChatMessageListResponse(      //채팅 메시지 내역 조회 요청 DTO
            Long messageId,
            Long senderId,
            String senderNickname,
            String content,
            LocalDateTime createdAt,
            MessageType type
    ) {}

}
