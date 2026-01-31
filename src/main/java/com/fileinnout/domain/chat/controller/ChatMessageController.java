package com.fileinnout.domain.chat.controller;

import com.fileinnout.domain.chat.ChatService;
import com.fileinnout.domain.chat.model.ChatMessageDto;
import com.fileinnout.domain.chat.model.ChatRoomDto;
import com.fileinnout.global.BaseResponse;
import com.fileinnout.global.Controller;
import com.fileinnout.utils.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class ChatMessageController implements Controller {
    private final ChatService chatService;

    public ChatMessageController(ChatService chatService) {
        this.chatService = chatService;
    }


    @Override
    public BaseResponse process(HttpServletRequest req, HttpServletResponse resp) {
        if(req.getRequestURI().contains("send") && req.getMethod().equals("POST")) {
            ChatMessageDto.ChatMessageSendReq reqdto = JsonParser.from(req, ChatMessageDto.ChatMessageSendReq.class);
            ChatMessageDto.ChatMessageSendRes resdto = chatService.Send(reqdto);
            return BaseResponse.success(resdto);

        } else if (req.getRequestURI().contains("read") && req.getMethod().equals("POST")) {
            ChatMessageDto.ChatMessageListReq reqDto = JsonParser.from(req, ChatMessageDto.ChatMessageListReq.class);
            List<ChatMessageDto.ChatMessageListRes> resList = chatService.Read(reqDto.roomId());
            return BaseResponse.success(resList);
        }

        return BaseResponse.fail("잘못된 요청 경로 또는 메서드입니다.");
    }
}
