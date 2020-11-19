package com.victolee.board.domain.repository;

import com.victolee.board.domain.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {


    //    @Query("SELECT distinct c.board.id from CartEntity as c where c.user.id ='jaebin129'")
    List<CartEntity> findByUserId(String userid); //장바구니를 저장한 유저의 것만 특정한 칼럼만 출력 시키기 위한 쿼리 메소드

    List<CartEntity> findByUserIdAndBoardId(String userid,Long boardid);

}