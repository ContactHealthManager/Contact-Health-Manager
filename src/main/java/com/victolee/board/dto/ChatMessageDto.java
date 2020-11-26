package com.victolee.board.dto;

import com.victolee.board.domain.entity.ChatMessageEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ChatMessageDto{ //지우면안됨!!!!! 이거 다른거임

    private Long id;
    private String roomid; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
    private LocalDateTime createdDate;// 등록날짜

    public ChatMessageDto(){}


    @Builder
    public ChatMessageDto(Long id, String roomid, String sender, String message, LocalDateTime createdDate) {
        this.id = id;
        this.roomid = roomid;
        this.sender = sender;
        this.message = message;
        this.createdDate =createdDate;
    }

    public ChatMessageEntity toEntity() { //저장을 위한 엔티티
        ChatMessageEntity chatMessageEntity = ChatMessageEntity.builder()
                .id(id)
                .sender(sender)
                .message(message)
                .roomid(roomid)
                .build();

        return chatMessageEntity;
    }
}
