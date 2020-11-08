package com.victolee.board.dto;


import com.victolee.board.domain.entity.BoardEntity;
import com.victolee.board.domain.repository.BoardIdAddress;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardIdAddressDto {
    // 게시글고유번호
    private Long id;

    // 게시물 주소
    private String address;

    private double x;

    private double y;


    @Builder
    public BoardIdAddressDto(Long id, String address, double x, double y) {
        this.id = id;
        this.address = address;
        this.x = x;
        this.y = y;
    }
}