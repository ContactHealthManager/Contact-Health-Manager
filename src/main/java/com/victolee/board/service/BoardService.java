package com.victolee.board.service;

import com.victolee.board.domain.entity.BoardEntity;
import com.victolee.board.domain.repository.BoardRepository;
import com.victolee.board.dto.BoardDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;

    private static final int BLOCK_PAGE_NUM_COUNT = 6;  // 블럭에 존재하는 페이지 번호 수
    private static final int PAGE_POST_COUNT = 6;       // 한 페이지에 존재하는 게시글 수

//    @Transactional
//    public void increaseBoardCount(Long no) {
//    countRepository.increaseBoardCount(no);
//    }


    @Transactional
    public List<BoardDto> getBoardlist(Integer pageNum) { //게시물 목록을 그 페이지에 맞게 리스트에 담음.
        Page<BoardEntity> page = boardRepository.findAll(PageRequest.of(pageNum - 1, PAGE_POST_COUNT,
                Sort.by(Sort.Direction.ASC, "createdDate")));

        List<BoardEntity> boardEntities = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (BoardEntity boardEntity : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(boardEntity));

        }

        return boardDtoList;
    }

    @Transactional
    public List<BoardDto> getBoardlistcount() {
        Page<BoardEntity> page = boardRepository.findAll(PageRequest.of(0,5,Sort.by(Sort.Direction.DESC, "bcount")));
        List<BoardDto> boardDtoList = new ArrayList<>(5);
        List<BoardEntity> boardEntities = page.getContent();

        for (BoardEntity boardEntity : boardEntities) {


            boardDtoList.add(this.convertEntityToDto(boardEntity));


        }


        return boardDtoList;

    }

    @Transactional //게시물의 총개수
    public Long getBoardCount() {
        return boardRepository.count();
    }


    @Transactional
    public BoardDto getPost(Long id) { //게시물 상세정보 수정할때,게시물 테이블의 여러 상세 정보를 객체에 담음
        Optional<BoardEntity> boardEntityWrapper = boardRepository.findById(id);
        BoardEntity boardEntity = boardEntityWrapper.get();

        return this.convertEntityToDto(boardEntity);
    }


    @Transactional
    public Long savePost(BoardDto boardDto) { // 게시물 저장하기

        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public void deletePost(Long id) {// 게시물 삭제
        boardRepository.deleteById(id);
    }

    @Transactional
    public List<BoardDto> searchPosts(String keyword) { // 게시물 검색 , 키워드는 제목과 내용
        List<BoardEntity> boardEntities = boardRepository
                .findByTitleContainingOrContentContaining(keyword, keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if (boardEntities.isEmpty()) return boardDtoList;

        for (BoardEntity boardEntity : boardEntities) {
            boardDtoList.add(this.convertEntityToDto(boardEntity));
        }

        return boardDtoList;
    }

    public Integer[] getPageList(Integer curPageNum) {  //pagenation
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 갯수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());

        // 총 게시글 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int) (Math.ceil((postsTotalCount / PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        // 페이지 번호 할당
        for (int val = curPageNum, idx = 0; val <= blockLastPageNum; val++, idx++) {
            pageList[idx] = val;
        }

        return pageList;
    }

    private BoardDto convertEntityToDto(BoardEntity boardEntity) { //엔티티 객체 변수를 디티오 객체 변수로 변환
        return BoardDto.builder()
                .id(boardEntity.getId())
                .title(boardEntity.getTitle())
                .content(boardEntity.getContent())
                .createdDate(boardEntity.getCreatedDate())
                .companyphone(boardEntity.getCompanyphone())
                .companyname(boardEntity.getCompanyname())
                .bcount(boardEntity.getBcount())
                .sumlike(boardEntity.getSumlike())
                .address(boardEntity.getAddress())
                .writer(boardEntity.getWriter())
                .imgname(boardEntity.getImgname())
                .build();
    }


}