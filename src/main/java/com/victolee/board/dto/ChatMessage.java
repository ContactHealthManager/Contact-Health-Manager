package com.victolee.board.dto;


import com.victolee.board.domain.entity.ChatMessageEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMessage {

    public ChatMessage() { }

    @Builder
    public ChatMessage(MessageType type, long id, String roomid, String sender, String message, long userCount) {
        this.type = type;
        this.id = id;
        this.roomid = roomid;
        this.sender = sender;
        this.message = message;
        this.userCount = userCount;

    }

    // 메시지 타입 : 입장, 퇴장, 채팅
    public enum MessageType {
        ENTER, QUIT, TALK
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


    private MessageType type; // 메시지 타입
    private long id; // 방번호
    private String roomid; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
    private long userCount; // 채팅방 인원수, 채팅방 내에서 메시지가 전달될때 인원수 갱신시 사용


}