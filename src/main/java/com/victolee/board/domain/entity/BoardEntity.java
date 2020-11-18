package com.victolee.board.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.text.DecimalFormat;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "board")
public class BoardEntity extends TimeEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false, length = 100000000)
    private String content;

    @Column(name = "companyphone", length = 13, nullable = false)
    private String companyphone;

    @Column(name = "companyname", length = 10, nullable = false)
    private String companyname;

    @Column(name = "bcount", nullable = false)
    private Integer bcount;

    @Column(name = "sumlike", nullable = false)
    private Integer sumlike;

    @Column(name = "address", columnDefinition = "TEXT", nullable = false)
    private String address;

    @Column(name = "writer", length = 10, nullable = false)
    private String writer;

    @Column(name = "imgname", length = 100, nullable = false)
    private String imgname;

    @Column(name = "x", nullable = false)
    private double x;

    @Column(name = "y", nullable = false)
    private double y;

    public Long getId() {
        return id;
    }


//    @ManyToMany
//    @JoinTable(name = "cart")
//    private List<BoardEntity> boardEntityList = new ArrayList<>();
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "board")
//    private List<CartEntity> cartEntityList = new ArrayList<CartEntity>();
//
//    public void addCart(CartEntity cartEntity)
//    {
//        cartEntityList.add(cartEntity);
//        cartEntity.setBoard(this);
//    }


    @Builder // 빌더 패턴 클래스 생성, 생성자에 포함된 필드만 포함
    public BoardEntity(Long id, String title, String content,
                       String companyphone, String companyname,
                       Integer bcount, Integer sumlike,
                       String address, String writer, String imgname,
                       double x, double y) {

        this.id = id;
        this.title = title;
        this.content = content;
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
