package com.victolee.board.domain.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Getter
@Entity
@Table(name = "chatmessage")
public class ChatMessageEntity {

    @Id
    @Column(name = "roomid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)//방번호
    private String roomid;

    public String getRoomid() {
        return roomid;
    }

    @Column(name = "sender", length = 100, nullable = false)
    private String sender;

    @Column(name = "message", length = 100, nullable = false)
    private String message;




    @Builder // 빌더 패턴 클래스 생성, 생성자에 포함된 필드만 포함
    public ChatMessageEntity(String roomid, String sender, String message) {

        this.roomid = roomid;

        this.sender = sender;
        this.message = message;

    }


}