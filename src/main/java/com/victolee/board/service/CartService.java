package com.victolee.board.service;

import com.victolee.board.domain.entity.CartEntity;
import com.victolee.board.domain.repository.BoardRepository;
import com.victolee.board.domain.repository.CartRepository;
import com.victolee.board.domain.repository.UserRepository;
import com.victolee.board.dto.CartDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public Long saveCart(CartDto cartDto){

        List<CartEntity> cartEntities =cartRepository.findByUserIdAndBoardId(cartDto.getUser().getId(),cartDto.getBoard().getId());
        // 테이블안에 있는 게시물들을 가져오기위해서 잠깐 출력함.
        List<CartDto> cartDtoList = new ArrayList<>();
        for(CartEntity cartEntity : cartEntities){
            cartDtoList.add(this.convertEntityToDto(cartEntity));
        }

        // 똑같은 게시물을 중복해서 장바구니에 담을수 없게 만듬.
        // 그 게시물의 번호랑 지금 테이블 안에 있는 게시물의 번호 들이랑
        Loop:
        for(int i=0;i<cartDtoList.size();i++) {
            if (!(cartDto.getBoard().getId().equals(cartDtoList.get(i).getBoardId()))) {
                break Loop;
            } else {
                CartDto cartDto1 = new CartDto();
                cartDto1.setId(cartEntities.get(i).getId());
                cartDto1.setBoard(cartEntities.get(i).getBoard());
                cartDto1.setStatus(1);
                cartDto1.setUser(cartEntities.get(i).getUser());

                return cartRepository.save(cartDto1.toEntity()).getId();
            }
        }

        return cartRepository.save(cartDto.toEntity()).getId();
    }

    @Transactional
    public List<CartDto> getCartlist(){ // 이거는 잘못 사용했습니다. 카트 테이블에 있는거 전부다 꺼내버렸습니다.


        List<CartEntity> cartEntities = cartRepository.findAll();

        List<CartDto> cartDtoList = new ArrayList<>();

        for(CartEntity cartEntity : cartEntities){
            cartDtoList.add(this.convertEntityToDto(cartEntity));
        }

        return cartDtoList;
    }

    @Transactional
    public List<CartDto> getCartlistUser(String loginId){  // 카트테이블에 있는것중에 유저가 장바구니에 고른것들만 표시하게함


        List<CartEntity> cartEntities = cartRepository.findByUserId(loginId);

        List<CartDto> cartDtoList = new ArrayList<>();

        for(CartEntity cartEntity : cartEntities){
            cartDtoList.add(this.convertEntityToDto(cartEntity));
        }

        return cartDtoList;
    }

//    @Transactional
//    public List<CartDto> getCartlistBoard(){  // 카트테이블에 있는것중에 보트테이블이 중복된것을 뺀다.
//
//
//        List<CartEntity> cartEntities = cartRepository.findDistinctByBoard_Id();
//
//        List<CartDto> cartDtoList = new ArrayList<>();
//
//        for(CartEntity cartEntity : cartEntities){
//            cartDtoList.add(this.convertEntityToDto(cartEntity));
//        }
//
//        return cartDtoList;
//    }

    @Transactional
    public void deleteCart(Long id) {// 게시물 삭제
        cartRepository.deleteById(id);
    }


    private CartDto convertEntityToDto(CartEntity cartEntity) { //엔티티 객체 변수를 디티오 객체 변수로 변환
        return CartDto.builder()
                .id(cartEntity.getId())
                .status(cartEntity.getStatus())
                .user(cartEntity.getUser())
                .board(cartEntity.getBoard())
                .userId(cartEntity.getUser().getId())
                .boardId(cartEntity.getBoard().getId())
                .title(cartEntity.getBoard().getTitle())
                .writer(cartEntity.getBoard().getWriter())
                .imgname(cartEntity.getBoard().getImgname())
                .build();
    }

}

