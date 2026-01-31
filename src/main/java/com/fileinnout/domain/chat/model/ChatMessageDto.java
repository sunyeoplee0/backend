package com.fileinnout.domain.chat.model;

import com.fileinnout.domain.chat.MessageType;

import java.time.LocalDateTime;

public class ChatMessageDto {                   //채팅 메세지 전송 요청 DTO
    public record ChatMessageSendReq(
            Long roomId,
            String content,
            MessageType type,
            Long senderId,
            String senderNickname
    ) {}

    public record ChatMessageSendRes(      //채팅 메세지 전송 응답 DTO
            Long messageId,
            String senderNickname,
            String content,
            Long senderId,
            LocalDateTime createdAt
    ) {}

    public record ChatMessageListReq(       //채팅 메시지 내역 조회 요청 DTO
            Long roomId,
            Long lastMessageId, // No-offset 페이징용
            int size
    ) {}

    public record ChatMessageListRes(      //채팅 메시지 내역 조회 요청 DTO
            Long messageId,
            Long senderId,
            String senderNickname,
            String content,
            LocalDateTime createdAt,
            MessageType type
    ) {}

}
