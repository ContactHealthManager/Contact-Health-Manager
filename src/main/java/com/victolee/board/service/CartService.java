package com.victolee.board.service;

import com.victolee.board.domain.entity.CartEntity;
import com.victolee.board.domain.repository.CartRepository;
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

    @Transactional
    public Long saveCart(CartDto cartDto){

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


        List<CartEntity> cartEntities = cartRepository.findCartEntityByUser_Id(loginId);

        List<CartDto> cartDtoList = new ArrayList<>();

        for(CartEntity cartEntity : cartEntities){
            cartDtoList.add(this.convertEntityToDto(cartEntity));
        }

        return cartDtoList;
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
                .build();
    }

}


