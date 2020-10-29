package com.victolee.board.domain.repository;


import com.victolee.board.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findById(String id);
    }

