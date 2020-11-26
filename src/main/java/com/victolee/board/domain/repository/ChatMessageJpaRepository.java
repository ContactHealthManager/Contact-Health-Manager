package com.victolee.board.domain.repository;

import com.victolee.board.domain.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageEntity, Long> {

}