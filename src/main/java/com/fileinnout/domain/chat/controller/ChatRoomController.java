package com.fileinnout.domain.chat.controller;

import com.fileinnout.domain.chat.ChatService;
import com.fileinnout.domain.chat.model.ChatRoomDto;
import com.fileinnout.global.BaseResponse;
import com.fileinnout.global.Controller;
import com.fileinnout.utils.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public class ChatRoomController implements Controller {
    private final ChatService chatService;

    public ChatRoomController(ChatService chatService) {
        this.chatService = chatService;
    }


    @Override
    public BaseResponse process(HttpServletRequest req, HttpServletResponse resp) {
        if(req.getRequestURI().contains("create") && req.getMethod().equals("POST")) {
            ChatRoomDto.ChatRoomCreateReq reqdto = JsonParser.from(req, ChatRoomDto.ChatRoomCreateReq.class);
            ChatRoomDto.ChatRoomCreateRes resdto = chatService.CreateRoom(reqdto);
            return BaseResponse.success(resdto);
        } else if (req.getRequestURI().contains("list") && req.getMethod().equals("POST")) {
            // 이제 GET이 아니므로 JsonParser 사용 가능!
            ChatRoomDto.ChatRoomListReq reqDto = JsonParser.from(req, ChatRoomDto.ChatRoomListReq.class);

            // 서비스 호출 및 결과 리스트 반환
            List<ChatRoomDto.ChatRoomListRes> resList = chatService.ReadList(reqDto.userIdx());
            return BaseResponse.success(resList);
        }

        return BaseResponse.fail("잘못된 요청 경로 또는 메서드입니다.");
    }
}
