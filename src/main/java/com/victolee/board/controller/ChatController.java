package com.victolee.board.controller;


import com.victolee.board.domain.entity.ChatMessageEntity;
import com.victolee.board.domain.repository.ChatMessageJpaRepository;
import com.victolee.board.domain.repository.ChatRoomRepository;
import com.victolee.board.dto.CartDto;
import com.victolee.board.dto.ChatMessage;
import com.victolee.board.service.ChatService;
import com.victolee.board.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import java.util.List;

//import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {


    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;

    @Autowired
    private ChatMessageJpaRepository chatMessageJpaRepository;
    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     * @return
     */

    @MessageMapping("/chat/message")
    public String message(ChatMessage message, @Header("token") String token) {
        String userId = jwtTokenProvider.getUserNameFromJwt(token);
        // 로그인 회원 정보로 대화명 설정
        message.setSender(userId);
        // 채팅방 인원수 세팅

        message.setUserCount(chatRoomRepository.getUserCount(message.getRoomid()));
        // Websocket에 발행된 메시지를 redis로 발행(publish)
        chatService.sendChatMessage(message);
        ChatMessageEntity chatMessageEntity = message.toEntity();
        ChatMessageEntity saved = chatMessageJpaRepository.save(chatMessageEntity);
        return saved.getMessage();

    }

}