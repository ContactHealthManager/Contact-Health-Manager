package com.victolee.board.service;

// import 생략...

import com.victolee.board.domain.entity.ChatMessageEntity;
import com.victolee.board.domain.repository.ChatMessageJpaRepository;
import com.victolee.board.domain.repository.ChatRoomRepository;
import com.victolee.board.dto.ChatMessage;
import com.victolee.board.dto.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ChatService {
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
    private ChatMessageJpaRepository chatMessageJpaRepository;

    @Transactional //방목록들을 저장해주는 save
    public String saveChatMessage(ChatMessage chatMessage) {
        return chatMessageJpaRepository.save(chatMessage.toEntity()).getMessage();
    }

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final ChatRoomRepository chatRoomRepository;


    /**
     * destination정보에서 roomid 추출
     */

    public String getRoomid(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1)
            return destination.substring(lastIndex + 1);
        else
            return "";
    }

    /**
     * 채팅방에 메시지 발송
     */
    public void sendChatMessage(ChatMessage chatMessage) {

        chatMessage.setUserCount(chatRoomRepository.getUserCount(String.valueOf(chatMessage.getRoomid())));
        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에 입장했습니다.");
            chatMessage.setSender("[알림]");
        } else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에서 나갔습니다.");
            chatMessage.setSender("[알림]");
        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }

    @Transactional
    public List<ChatMessage> getchatmessagelist(String roomid){
//        hashOpsChatRoom.values(CHAT_ROOMS);
        List<ChatMessageEntity> chatMessageEntities = chatMessageJpaRepository.findByRoomid(roomid, Sort.by(Sort.Direction.DESC, "id"));

        List<ChatMessage> chatMessageList = new ArrayList<>();

        for(ChatMessageEntity chatMessageEntity : chatMessageEntities){
            chatMessageList.add(this.convertEntityToDto(chatMessageEntity));
        }


        return chatMessageList;
    }

    private ChatMessage convertEntityToDto(ChatMessageEntity chatMessageEntity) { //엔티티 객체 변수를 디티오 객체 변수로 변환
        return ChatMessage.builder()
                .type(chatMessageEntity.getType())
                .userCount(chatMessageEntity.getUserCount())
                .id(chatMessageEntity.getId())
                .roomid(chatMessageEntity.getRoomid())
                .sender(chatMessageEntity.getSender())
                .message(chatMessageEntity.getMessage())
                .build();
    }

}