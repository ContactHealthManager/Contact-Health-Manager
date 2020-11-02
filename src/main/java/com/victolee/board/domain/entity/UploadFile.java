package com.victolee.board.domain.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "upload")
public class UploadFile {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String fileName;

    @Column
    private String saveFileName;

    @Column
    private String filePath;

    @Column
    private String contentType;

    @Column
    private Long size;

    @Column
    private LocalDateTime registerDate;


    @Builder
    public UploadFile(Long id, String fileName, String saveFileName,
                      String filePath, String contentType,
                      Long size, LocalDateTime registerDate) {
        this.id = id;
        this.fileName = fileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
        this.contentType = contentType;
        this.size = size;
        this.registerDate = registerDate;
    }

    @Builder
    public UploadFile() {

    }
}
