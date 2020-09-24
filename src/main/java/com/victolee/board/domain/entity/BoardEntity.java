package com.victolee.board.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "board")
public class BoardEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pId;

    @Column(length = 10, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 13, nullable = false)
    private String cNumber;

    @Column(length = 10, nullable = false)
    private String cName;

    @Column(nullable = false)
    private Integer postCount;

    @Column(nullable = false)
    private Integer postLike;

    @Column(nullable = false)
    private Integer commentId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String pAddress;


    @Builder
    public BoardEntity(Long pId, String title, String content,
                       String writer,String cNumber,String cName,
                       Integer postCount,Integer postLike,Integer commentId,
                       String pAddress) {
        this.pId = pId;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.cNumber = cNumber;
        this.cName = cName;
        this.postCount = postCount;
        this.postLike = postLike;
        this.commentId = commentId;
        this.pAddress = pAddress;
    }
}
