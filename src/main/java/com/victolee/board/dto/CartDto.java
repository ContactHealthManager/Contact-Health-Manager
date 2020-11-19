package com.victolee.board.dto;

import com.victolee.board.domain.entity.BoardEntity;
import com.victolee.board.domain.entity.CartEntity;
import com.victolee.board.domain.entity.UserEntity;
import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class CartDto {

    // 장바구니 번호
    private Long id;

    // 상태
    private int status;

    // 유저고유번호
    private UserEntity user;

    // 게시물 고유번호
    private BoardEntity board;

    private String userId ; //userEntity 객체 user에서 id를 빼온다.

    private Long boardId ; // boardEntity 객체 board에서 id를 빼온다

    private String title; //boardEntity 객체 board에서 title을 빼온다

    private String writer; //boardEntity 객체 board에서 writer을 빼온다.

    private String imgname;



    public CartEntity toEntity() { //저장을 위한 엔티티
        CartEntity cartEntity = CartEntity.builder()
                .id(id)
                .status(status)
                .user(user)
                .board(board)
                .build();

        return cartEntity;
    }


    @Builder
    public CartDto(Long id, Integer status, UserEntity user,
                   BoardEntity board,String userId,Long boardId,
                   String title,String writer,String imgname) {
        this.id = id;
        this.status = status;
        this.user = user;
        this.board = board;
        this.userId = userId;
        this.boardId = boardId;
        this.title = title;
        this.writer = writer;
        this.imgname = imgname;
    }
}