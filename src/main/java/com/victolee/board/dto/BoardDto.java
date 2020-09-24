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
    private Long pId;

    // 작성자
    private String writer;

    // 글 제목
    private String title;

    // 글 내용
    private String content;

    // 소속전화번호
    private String cNumber;

    // 소속이름
    private String cName;

    // 조회수
    private int postCount;

    // 좋아요수
    private int postLike;

    // 등록날짜
    private LocalDateTime createdDate;

    // 수정날짜
    private LocalDateTime modifiedDate;

    // 댓글번호
    private int commentId;

    // 게시물 주소
    private String pAddress;

    public BoardEntity toEntity(){
        BoardEntity boardEntity = BoardEntity.builder()
                .pId(pId)
                .writer(writer)
                .title(title)
                .content(content)
                .cNumber(cNumber)
                .cName(cName)
                .postCount(postCount)
                .postLike(postLike)
                .commentId(commentId)
                .pAddress(pAddress)
                .build();
        return boardEntity;
    }

    @Builder
    public BoardDto(Long pId, String title, String content, String writer,
                    LocalDateTime createdDate, LocalDateTime modifiedDate,
                    String cNumber,String cName,Integer postCount,
                    Integer postLike,Integer commentId,String pAddress) {
        this.pId = pId;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.cNumber = cNumber;
        this.cName = cName;
        this.postCount = postCount;
        this.postLike = postLike;
        this.commentId = commentId;
        this.pAddress = pAddress;
    }
}
