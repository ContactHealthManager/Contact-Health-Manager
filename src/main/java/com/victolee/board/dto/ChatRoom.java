package com.victolee.board.dto;



import com.victolee.board.domain.entity.ChatMessageEntity;
import com.victolee.board.domain.entity.ChatRoomEntity;
import lombok.*;


import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    private String roomid;

    public String getroomid() {
        return roomid;
    }

    private String name;
    private long userCount; // 채팅방 인원수

    public static ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomid = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }

    public ChatRoomEntity toEntity() { //저장을 위한 엔티티
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                .roomid(roomid)
                .name(name)
                .build();

        return chatRoomEntity;
    }


    @Builder
    public ChatRoom(String roomid, String name){
        this.roomid = roomid;
        this.name = name;
    }



}