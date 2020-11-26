package com.victolee.board.domain.repository;


import com.victolee.board.domain.entity.CartEntity;
import com.victolee.board.domain.entity.ChatMessageEntity;

import com.victolee.board.dto.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageEntity, Long> {

}

