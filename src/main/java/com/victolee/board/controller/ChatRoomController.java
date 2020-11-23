package com.victolee.board.controller;


import com.victolee.board.domain.repository.ChatRoomRepository;
import com.victolee.board.dto.ChatMessage;
import com.victolee.board.dto.ChatRoom;
import com.victolee.board.dto.UserInfoDto;
import com.victolee.board.service.ChatRoomService;
import com.victolee.board.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {



    @Autowired
    private ChatRoomService chatRoomService;
    private final ChatRoomRepository chatRoomRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @GetMapping("/room") //채팅방 목록 페이지로 연결
    public String rooms(Model model) {
        return "/chat/room";
    }
    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllRoom();
        chatRooms.stream().forEach(room -> room.setUserCount(chatRoomRepository.getUserCount(room.getRoomid())));

        return chatRooms;
    }

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        ChatRoom createChatRoom = chatRoomRepository.createChatRoom(name);


        //         채팅방 목록을 저장 한다.
       chatRoomService.saveChatRoom(createChatRoom);


        return createChatRoom; //chatRoomRepository의 채팅방 생성 함수를 사용해 채팅방 생성.
    }


    @GetMapping("/room/enter/{roomid}") //채팅방 목록중에 한 채팅방 안으로 들어감
    public String roomDetail(Model model, @PathVariable String roomid) {
        model.addAttribute("roomid", roomid);
        return "/chat/roomdetail";
    }

    @GetMapping("/room/{roomid}") //채팅방 목록중 한 채팅방 찾기
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomid) {
        return chatRoomRepository.findRoomById(roomid);
    }
    //로그인 유저 정보 조회
    @GetMapping("/user")
    @ResponseBody
    public UserInfoDto getUserInfo(Principal principal) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userid = principal.getName();
        return UserInfoDto.builder().id(userid).token(jwtTokenProvider.generateToken(userid)).build();
    }


}