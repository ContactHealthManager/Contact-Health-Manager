package com.victolee.board.dto;

import com.victolee.board.domain.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    // 게시글고유번호
    private Long id;

    // 글 제목
    private String title;

    // 글 내용
    private String content;

    // 소속전화번호
    private String companyphone;

    // 소속이름
    private String companyname;
    // 작성자
    private String writer;
    // 조회수

    private int bcount;

    // 좋아요총수
    private int sumlike;

    // 등록날짜
    private LocalDateTime createdDate;

    // 수정날짜
    private LocalDateTime modifiedDate;

    // 게시물 주소
    private String address;

    private String imgname;

    private double x;

    private double y;


    public BoardEntity toEntity() { //글쓰기 저장을 위한 엔티티
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .companyphone(companyphone)
                .companyname(companyname)
                .bcount(bcount)
                .sumlike(sumlike)
                .address(address)
                .writer(writer)
                .imgname(imgname)
                .x(x)
                .y(y)
                .build();
        return boardEntity;
    }


    @Builder
    public BoardDto(Long id, String title, String content,
                    LocalDateTime createdDate, LocalDateTime modifiedDate,
                    String companyphone, String companyname, Integer bcount,
                    Integer sumlike, String address, String writer, String imgname,
                    double x, double y) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.companyphone = companyphone;
        this.companyname = companyname;
        this.bcount = bcount;
        this.sumlike = sumlike;
        this.address = address;
        this.writer = writer;
        this.imgname = imgname;
        this.x = x;
        this.y = y;


    }


}