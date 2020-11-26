package com.victolee.board.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "chatmessage")
public class ChatMessageEntity {

    @Id
    @Column(name = "id")

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "roomid")
    private String roomid;


    @Column(name = "sender", length = 100 )
    private String sender;

    @Column(name = "message", length = 140)
    private String message;





    @Builder // 빌더 패턴 클래스 생성, 생성자에 포함된 필드만 포함
    public ChatMessageEntity(long id, String roomid, String sender, String message) {
        this.id = id;
        this.roomid = roomid;
        this.sender = sender;
        this.message = message;
        this.roomid = roomid;


    }


}