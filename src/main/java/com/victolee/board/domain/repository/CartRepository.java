package com.victolee.board.domain.repository;

import com.victolee.board.domain.entity.BoardEntity;
import com.victolee.board.domain.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
}
