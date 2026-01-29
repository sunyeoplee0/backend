package com.fileinnout.domain.chat;

import com.fileinnout.domain.chat.model.ChatRoomDto;
import com.fileinnout.domain.user.UserDto;
import com.fileinnout.global.BaseResponse;
import com.fileinnout.global.Controller;
import com.fileinnout.utils.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ChatController implements Controller {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @Override
    public BaseResponse process(HttpServletRequest req, HttpServletResponse resp) {

        return null;
    }
}
