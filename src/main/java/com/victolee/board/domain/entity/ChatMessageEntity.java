package com.victolee.board.domain.entity;


import com.victolee.board.dto.ChatMessage;

import lombok.*;


import javax.persistence.*;
import java.awt.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "chatmessage")
public class ChatMessageEntity {

    @Id
    @Column(name = "id")

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "roomid")
    private String roomid;


    @Column(name = "sender", length = 100, nullable = false)

    private String sender;

    @Column(name = "message", length = 140)
    private String message;

    @Column(name = "type", length = 100, nullable = false)
    private ChatMessage.MessageType type;

    @Column(name = "userCount", length = 100, nullable = false)
    private long userCount;

    public String getRoomid() {
        return roomid;
    }


    public enum MessageType{
        ENTER ,QUIT,TALK
    }

    @Builder // 빌더 패턴 클래스 생성, 생성자에 포함된 필드만 포함
    public ChatMessageEntity(ChatMessage.MessageType type, int id, String roomid, String sender, String message, long userCount) {
        this.type = type;
        this.userCount = userCount;
        this.id = id;
        this.roomid = roomid;
        this.sender = sender;
        this.message = message;
        this.roomid = roomid;


    }


}