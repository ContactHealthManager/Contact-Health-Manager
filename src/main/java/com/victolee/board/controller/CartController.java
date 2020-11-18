package com.victolee.board.controller;

import com.victolee.board.domain.entity.BoardEntity;
import com.victolee.board.domain.entity.CartEntity;
import com.victolee.board.domain.entity.UserEntity;
import com.victolee.board.dto.BoardDto;
import com.victolee.board.dto.CartDto;
import com.victolee.board.service.BoardService;
import com.victolee.board.service.CartService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class CartController {
    @Autowired
    private CartService cartService;


    @RequestMapping(value = "/post/{no}", method = RequestMethod.POST) //카트에다가 저장.
    public String cartsave(CartDto cartDto) {

        cartService.saveCart(cartDto);

        return "redirect:/cart";
    }

    /* 게시글 목록 */ /*페이지를 담는 리스트와 그 페이지 안의 목록들을 담은 리스트 */
    @GetMapping("/cart")
    public String cartlist(Model model,Principal principal) {
        List<CartDto> cartList = cartService.getCartlist();

        model.addAttribute("loginId",principal.getName());
        model.addAttribute("cartList", cartList);

        return "/cart";
    }
//리스트들의 담은것이 카트번호 id,카트 상태 , status


}