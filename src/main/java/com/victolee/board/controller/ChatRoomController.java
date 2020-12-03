package com.victolee.board.controller;


import com.victolee.board.domain.entity.UserEntity;
import com.victolee.board.domain.repository.ChatRoomRepository;
import com.victolee.board.dto.BoardDto;
import com.victolee.board.dto.ChatMessage;
import com.victolee.board.dto.ChatRoom;
import com.victolee.board.dto.UserInfoDto;
import com.victolee.board.service.ChatRoomService;
import com.victolee.board.service.ChatService;
import com.victolee.board.service.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {


    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private ChatService chatService;
    private final ChatRoomRepository chatRoomRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @GetMapping("/room") //채팅방 목록 페이지로 연결
    public String rooms(Model model) {
        return "/chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room(Principal principal) { // 채팅방 목록 모든 채팅방을 보여준다. 모든 조회.
        // 이거원래 채팅방 생성되면 생성된거 다보여주는 건데 DB에 저장안되서 다시짰음. 밑에 있는게 다보여주는거
//        List<ChatRoom> chatRooms = chatRoomRepository.findAllRoom();
        String username = principal.getName();
        List<ChatRoom> chatRooms = new ArrayList<>();

        List<ChatRoom> farawaychatRooms =chatRoomService.getchatroomlist();

//        System.out.println(farawaychatRooms);

        for (int i=0; i<farawaychatRooms.size(); i++) {
            if(farawaychatRooms.get(i).getName().contains(username))
                chatRooms.add(farawaychatRooms.get(i));

        }

        chatRooms.stream().forEach(room -> room.setUserCount(chatRoomRepository.getUserCount(room.getroomid())));
        return chatRooms;
    }


    @PostMapping("/room") //채팅방 생성
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        ChatRoom createChatRoom = chatRoomRepository.createChatRoom(name);

        //         채팅방 목록을 저장 한다.
        chatRoomService.saveChatRoom(createChatRoom);

        return createChatRoom; //chatRoomRepository의 채팅방 생성 함수를 사용해 채팅방 생성.
    }

    @GetMapping("/room/enter/{roomId}") //채팅방 목록중에 한 채팅방 안으로 들어감

    public String roomDetail(Model model, @PathVariable String roomId,Principal principal) {

        ModelAndView view = new ModelAndView();
        List<ChatMessage> chatMessage = chatService.getchatmessagelist(roomId);

        model.addAttribute("userId",principal.getName());
        model.addAttribute("roomId", roomId);
        model.addAttribute("chatMessage", chatMessage);


        return "/chat/roomdetail";
    }

    @GetMapping("/room/{roomid}") //채팅방 목록중 한 채팅방 찾기
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomid) {
        return chatRoomRepository.findRoomById(roomid);
    }

    @GetMapping("/user")
    @ResponseBody
    public UserInfoDto getUserInfo(Principal principal) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userid = principal.getName();
        return UserInfoDto.builder().id(userid).token(jwtTokenProvider.generateToken(userid)).build();
    }


}