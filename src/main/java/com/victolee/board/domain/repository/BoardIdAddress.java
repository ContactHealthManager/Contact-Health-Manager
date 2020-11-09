package com.victolee.board.domain.repository;



public interface BoardIdAddress {   // 보드테이블에서 특정 칼럼 , 아이디 ,주소 , 좌표 x ,y 값만 불러오게 하기위한 getter

    Long getId();
    String getAddress();
    double getX();
    double getY();
    String getTitle();
    String getWriter();


}
