package com.fileinnout.domain.chat.model;

import java.time.LocalDateTime;
import java.util.List;
// ChatRoom = CR로 표기
public class ChatRoomDto {

    public record ChatRoomCreateReq(        // 채팅방 생성 요청 DTO
            String title,               // 채팅방 이름
            List<Long> participantIdx   // 채팅 초대 대상자
    ) {}
    public record ChatRoomCreateRes(   // 채팅방 생성 응답 DTO
            Long roomId,                    // 채팅방 번호
            String title,                   // 채팅방 이름
            LocalDateTime createdAt         // 채팅방 생성시간
    ) {}

    public record ChatRoomListReq(
            Long userIdx,//채팅방 목록 조회 요청
            int page,
            int size
    ) {}

    public record ChatRoomListRes(     //채팅방 목록 조회 응답
            Long roomId,          // 채팅방 번호
            String title,         // 채팅방 이름
            String lastMessage,   // 마지막 메세지
            String lastSentAt,    // 마지막 채팅 시간
            int participantCount, // 현재인원
            int unreadCount       // 확인안한 채팅 수
    ) {}
}