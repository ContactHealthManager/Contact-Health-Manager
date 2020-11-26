package com.victolee.board.domain.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Getter
@Entity
@Table(name = "chatroom")
public class ChatRoomEntity {

    @Id
    @Column(name = "roomid" ,nullable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)//방번호
    private String roomid;

    public String getRoomid() {

        return roomid;
    }

    @Column(name = "name", length = 100, nullable = false)
    private String name;


    @Builder // 빌더 패턴 클래스 생성, 생성자에 포함된 필드만 포함
    public ChatRoomEntity(String roomid, String name){

        this.roomid = roomid;
        this.name = name;

    }


}