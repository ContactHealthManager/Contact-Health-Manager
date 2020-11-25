package com.victolee.board.service;



// import 생략...

import com.victolee.board.domain.entity.ChatMessageEntity;
import com.victolee.board.domain.entity.ChatRoomEntity;
import com.victolee.board.domain.repository.ChatMessageJpaRepository;
import com.victolee.board.domain.repository.ChatRoomJpaRepository;
import com.victolee.board.domain.repository.ChatRoomRepository;
import com.victolee.board.dto.ChatMessage;
import com.victolee.board.dto.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatService {

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
     * destination정보에서 roomId 추출
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
    public List<ChatMessage> getchatmessagelist(){

        List<ChatMessageEntity> chatMessageEntities = chatMessageJpaRepository.findAll();

        List<ChatMessage> chatMessageList = new ArrayList<>();

        for(ChatMessageEntity chatMessageEntity : chatMessageEntities){
            chatMessageList.add(this.convertEntityToDto(chatMessageEntity));
        }


        return chatMessageList;
    }

    private ChatMessage convertEntityToDto(ChatMessageEntity chatMessageEntity) { //엔티티 객체 변수를 디티오 객체 변수로 변환
        return ChatMessage.builder()
                .id(chatMessageEntity.getId())
                .roomid(chatMessageEntity.getRoomid())
                .sender(chatMessageEntity.getSender())
                .message(chatMessageEntity.getMessage())
                .build();
    }
}