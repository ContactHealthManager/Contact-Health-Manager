package com.victolee.board.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
//@NamedQuery(name="CartEntity.findDistinctBoardByUserId", query = "SELECT distinct c.board.id from CartEntity as c where c.user.id = 'jaebin129'")
@Table(name = "cart")
public class CartEntity {

    @Id
    @Column(name="id") //카트번호
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status",nullable = false) // 찜하기 1 , 찜취소 0
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "user_id")  // 유저 아이디
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "board_id") // 보드 아이디, joinColum의 name값과 테이블 명을 꼭 같게 해줘!!!!이틀새지말고
    private BoardEntity board;


    @Builder // 빌더 패턴 클래스 생성, 생성자에 포함된 필드만 포함
    public CartEntity(Long id,Integer status,UserEntity user,BoardEntity board) {
        this.id = id;
        this.status = status;
        this.user = user;
        this.board = board;
    }
}