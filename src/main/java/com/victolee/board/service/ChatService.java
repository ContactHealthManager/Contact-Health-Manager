package com.victolee.board.service;



// import 생략...

import com.victolee.board.domain.entity.ChatMessageEntity;
import com.victolee.board.domain.repository.ChatMessageJpaRepository;
import com.victolee.board.domain.repository.ChatRoomRepository;
import com.victolee.board.dto.ChatMessage;
import com.victolee.board.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageJpaRepository chatMessageJpaRepository;

    /**
     * destination정보에서 roomid 추출
     */
    public String getroomid(String destination) {
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
        chatMessage.setUserCount(chatRoomRepository.getUserCount(chatMessage.getroomid()));

        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에 입장했습니다.");
            chatMessage.setSender("[알림]");
        } else if (ChatMessage.MessageType.QUIT.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 방에서 나갔습니다.");
            chatMessage.setSender("[알림]");
        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
    }

    private ChatMessageDto convertEntityToDto(ChatMessageEntity chatMessageEntity) { //엔티티 객체 변수를 디티오 객체 변수로 변환
        return ChatMessageDto.builder()
                .id(chatMessageEntity.getId())
                .roomid(chatMessageEntity.getSender())
                .message(chatMessageEntity.getMessage())
                .roomid(chatMessageEntity.getRoomid())
                .build();
    }

    //메시지 저장
    @Transactional
    public Long saveChatMessage(ChatMessageDto chatMessagedto){
        return chatMessageJpaRepository.save(chatMessagedto.toEntity()).getId();
    }

}