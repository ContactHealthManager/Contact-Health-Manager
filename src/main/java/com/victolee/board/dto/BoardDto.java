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

    // 조회수
    private int count;

    // 좋아요수
    private int sumlike;

    // 등록날짜
    private LocalDateTime createdDate;

    // 수정날짜
    private LocalDateTime modifiedDate;

    // 게시물 주소
    private String address;

    // 작성자
    private String writer;

    public BoardEntity toEntity(){
        BoardEntity boardEntity = BoardEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .companyphone(companyphone)
                .companyname(companyname)
                .count(count)
                .sumlike(sumlike)
                .address(address)
                .writer(writer)
                .build();
        return boardEntity;
    }

    @Builder
    public BoardDto(Long id, String title, String content, String writer,
                    LocalDateTime createdDate, LocalDateTime modifiedDate,
                    String companyphone,String companyname,Integer count,
                    Integer sumlike,String address) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.companyphone = companyphone;
        this.companyname = companyname;
        this.count = count;
        this.sumlike = sumlike;
        this.address = address;
        this.writer = writer;
    }
}
