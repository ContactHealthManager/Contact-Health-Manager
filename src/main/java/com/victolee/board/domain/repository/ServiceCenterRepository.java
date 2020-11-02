package com.victolee.board.domain.repository;

import com.victolee.board.domain.entity.ServiceCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceCenterRepository extends JpaRepository<ServiceCenterEntity, Long> {

    List<ServiceCenterEntity> findByTitleContaining(String keyword);

}
