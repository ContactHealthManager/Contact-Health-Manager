package com.victolee.board.dto;


import lombok.*;

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

    private String title;

    private String writer;


    @Builder
    public BoardIdAddressDto(Long id, String address, double x, double y,String title,String writer) {
        this.id = id;
        this.address = address;
        this.x = x;
        this.y = y;
        this.title = title;
        this.writer = writer;
    }
}