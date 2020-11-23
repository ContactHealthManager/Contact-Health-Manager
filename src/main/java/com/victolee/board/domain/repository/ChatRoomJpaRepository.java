package com.victolee.board.domain.repository;

import com.victolee.board.domain.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomJpaRepository extends JpaRepository<ChatRoomEntity, Long> {

//    List<ChatRoomEntity> findByName(String name); // 나중에 방이름만 검색해서 나오게 할거임.
//       List<ChatRoomEntity> findByLogingIdContaning(String loginId);
}
