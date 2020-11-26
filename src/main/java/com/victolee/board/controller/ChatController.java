package com.victolee.board.controller;

import com.victolee.board.domain.repository.ChatMessageJpaRepository;
import com.victolee.board.domain.repository.ChatRoomRepository;
import com.victolee.board.dto.ChatMessage;
import com.victolee.board.dto.ChatMessageDto;
import com.victolee.board.service.ChatService;
import com.victolee.board.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {


    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatService chatService;
    private ChatMessageJpaRepository chatMessageJpaRepository;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */

    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("token") String token) {
        String userId = jwtTokenProvider.getUserNameFromJwt(token);
        // 로그인 회원 정보로 대화명 설정
        message.setSender(userId);
        // 채팅방 인원수 세팅
        message.setUserCount(chatRoomRepository.getUserCount(message.getroomid()));

        System.out.println(token);
//        message.setId((long) 1);
//        ChatMessageDto chatMessagedto = new ChatMessageDto();
//        chatMessagedto.setSender(message.getSender());
//        chatMessagedto.setMessage(message.getMessage());
//        chatMessagedto.setRoomid(message.getroomid());
//        chatService.saveChatMessage(chatMessagedto);

        System.out.println(message);



        // Websocket에 발행된 메시지를 redis로 발행(publish)
        chatService.sendChatMessage(message);
//        chatService.saveChatMessage(message);



    }
    @RequestMapping(value = "/chat/message", method = RequestMethod.POST) //카트에다가 저장.
    public String chatmessagesave(ChatMessageDto chatMessageDto) {


        chatService.saveChatMessage(chatMessageDto);


        return "redirect:/chat/room/enter/"+chatMessageDto.getRoomid();
    }

}