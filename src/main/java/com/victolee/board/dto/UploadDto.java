package com.victolee.board.dto;


import com.victolee.board.domain.entity.UploadFile;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UploadDto {

    private Long id;


    private String fileName;


    private String saveFileName;


    private String filePath;


    private String contentType;

    private Long size;


    private LocalDateTime registerDate;



    public UploadFile toEntity() {
        UploadFile uploadFile = UploadFile.builder()
                .id(id)
                .fileName(fileName)
                .saveFileName(saveFileName)
                .filePath(filePath)
                .contentType(contentType)
                .size(size)
                .registerDate(registerDate)
                .build();
        return uploadFile;
    }



}