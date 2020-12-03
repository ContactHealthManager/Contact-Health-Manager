package com.victolee.board.service;


import com.victolee.board.domain.entity.ChatRoomEntity;
import com.victolee.board.domain.repository.ChatMessageJpaRepository;
import com.victolee.board.domain.repository.ChatRoomJpaRepository;
import com.victolee.board.dto.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Service
public class ChatRoomService {
    private static final String CHAT_ROOMS = "CHAT_ROOM"; // 채팅룸 저장
    public static final String USER_COUNT = "USER_COUNT"; // 채팅룸에 입장한 클라이언트수 저장
    public static final String ENTER_INFO = "ENTER_INFO"; // 채팅룸에 입장한 클라이언트의 sessionId와 채팅룸 id를 맵핑한 정보 저장

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, ChatRoom> hashOpsChatRoom;
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOpsEnterInfo;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOps;

    @Autowired
    private ChatRoomJpaRepository chatRoomJpaRepository;
    @Autowired
    private ChatMessageJpaRepository chatMessageJpaRepository;

    @Transactional //방목록들을 저장해주는 save
    public String saveChatRoom(ChatRoom chatRoom) {
        return chatRoomJpaRepository.save(chatRoom.toEntity()).getRoomid();
    }

    @Transactional // 저장되어있었던 방목록들 전체를 출력해주는 getlist
    public List<ChatRoom> getchatroomlist(){

//        hashOpsChatRoom.values(CHAT_ROOMS);

        List<ChatRoomEntity> chatRoomEntities = chatRoomJpaRepository.findAll();

        List<ChatRoom> chatRoomList = new ArrayList<>();

        for(ChatRoomEntity chatRoomEntity : chatRoomEntities){
            chatRoomList.add(this.convertEntityToDto(chatRoomEntity));
        }

        return chatRoomList;
    }


    private ChatRoom convertEntityToDto(ChatRoomEntity chatRoomEntity) { //엔티티 객체 변수를 디티오 객체 변수로 변환
        return ChatRoom.builder()
                .roomid(chatRoomEntity.getRoomid())
                .name(chatRoomEntity.getName())
                .build();
    }
}