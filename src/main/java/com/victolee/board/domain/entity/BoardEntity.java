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
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 13, nullable = false)
    private String companyphone;

    @Column(length = 10, nullable = false)
    private String companyname;

    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private Integer sumlike;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String address;

    @Column(length = 10, nullable = false)
    private String writer;

    @Builder
    public BoardEntity(Long id, String title, String content,
                       String companyphone,String companyname,Integer count,
                       Integer sumlike,String address,
                       String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.companyphone = companyphone;
        this.companyname = companyname;
        this.count = count;
        this.sumlike = sumlike;
        this.address = address;
        this.writer = writer;
    }
}
