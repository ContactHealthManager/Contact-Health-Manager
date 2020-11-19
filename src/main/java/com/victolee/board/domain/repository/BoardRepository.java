package com.victolee.board.domain.repository;

import com.victolee.board.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// findByXXX: XXX 컬럼을 키워드로 검색
// Containing: 특정 키워드 포함 여부

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {



    List<BoardIdAddress> findAllBy(); //특정 열만 전체 보기 하기 위해서 특정열은 BoardIdAddress에 들어있다.

    List<BoardEntity> findByTitleContainingOrContentContaining(String titlekeyword,String contentkeyword);
    //제목과 내용을 검색하기 위한 쿼리 메소드

    Optional<BoardEntity> findById(Long id);




}

//
//    findBy로 시작하면 select 쿼리를 시작한다는 뜻입니다.
//        Content, Title는 이 컬럼에서 파라미터로 받은 값을 찾겠다는 의미입니다. (엔티티 클래스의 컬럼 참고)
//        Containing이 없다면 해당 키워드와 일치하는 결과만 찾고, 이 키워드가 있는 경우는 포함하는 결과를 찾습니다. 즉, SQL문의 like %xx% 와 비슷합니다.
//        IgnoreCase 키워드는 대소문자 구별을 하지 않는다는 의미입니다. 이것이 없다면 대소문자가 구별됩니다.
//