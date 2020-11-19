package com.victolee.board.domain.repository;

import com.victolee.board.domain.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {


    List<CartEntity> findCartEntityByUser_Id(String id); //장바구니를 저장한 유저의 것만 특정한 칼럼만 출력 시키기 위한 쿼리 메소드
}
