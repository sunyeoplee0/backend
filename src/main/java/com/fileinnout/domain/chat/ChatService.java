package com.fileinnout.domain.chat;

import com.fileinnout.domain.chat.model.ChatMessageDto;
import com.fileinnout.domain.chat.model.ChatRoomDto;

import java.util.List;

public class ChatService {
    private ChatRoomRepository chatRoomRepository;
    private ChatMessageRepository chatMessageRepository;

    public ChatService(ChatMessageRepository chatMessageRepository, ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
    }


    public ChatRoomDto.ChatRoomCreateRes CreateRoom(ChatRoomDto.ChatRoomCreateReq req){
        ChatRoomDto.ChatRoomCreateRes res = chatRoomRepository.create(req);
        return res;
    }

    public List<ChatRoomDto.ChatRoomListRes> ReadList(Long userId){
       List<ChatRoomDto.ChatRoomListRes> res = chatRoomRepository.read(userId);
       return res;
    }

    public ChatMessageDto.ChatMessageSendRes Send(ChatMessageDto.ChatMessageSendReq req){
        ChatMessageDto.ChatMessageSendRes res = chatMessageRepository.send(req);
        return res;
    }
}
