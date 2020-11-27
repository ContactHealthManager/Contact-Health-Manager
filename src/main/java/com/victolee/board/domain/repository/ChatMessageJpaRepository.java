package com.victolee.board.domain.repository;


import com.victolee.board.domain.entity.BoardEntity;
import com.victolee.board.domain.entity.CartEntity;
import com.victolee.board.domain.entity.ChatMessageEntity;

import com.victolee.board.domain.entity.ChatRoomEntity;
import com.victolee.board.dto.ChatMessage;
import com.victolee.board.dto.ChatRoom;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageEntity, Long> {
    List<ChatMessageEntity> findByRoomid(String roomid, Sort id);
}

