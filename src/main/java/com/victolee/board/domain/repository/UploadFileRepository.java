package com.victolee.board.domain.repository;

import com.victolee.board.domain.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long>{
}
