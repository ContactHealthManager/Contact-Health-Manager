package com.victolee.board.controller;

import com.victolee.board.dto.BoardDto;
import com.victolee.board.dto.CartDto;
import com.victolee.board.service.BoardService;
import com.victolee.board.service.CartService;
import lombok.AllArgsConstructor;
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
    @Autowired
    private BoardService boardService;


    /*장바구니 저장*/
    @RequestMapping(value = "/post/{no}", method = RequestMethod.POST) //카트에다가 저장.
    public String cartsave(CartDto cartDto, Principal principal) {

        // 작성자가 자기자신의 게시물을 찜할수 없도록 조건을 걸어줌.
        if(!cartDto.getBoard().getWriter().equals(principal.getName())) {
            cartService.saveCart(cartDto);
        }
        BoardDto boardDto = boardService.getPost(cartDto.getBoard().getId());

        if (!cartDto.getBoard().getWriter().equals(principal.getName())) { //저장했을때 , 좋아요 수 1증가
            int sumlike = cartDto.getBoard().getSumlike();
            sumlike = sumlike + 1;
            boardDto.setSumlike(sumlike);
            boardService.savePost(boardDto);
        }
        System.out.println(cartDto.getBoard());


        return "redirect:/cart";
    }

    /* 장바구니 게시글 목록 */ /*페이지를 담는 리스트와 그 페이지 안의 목록들을 담은 리스트 */
    @GetMapping("/cart")
    public String cartlist(Model model, Principal principal) {

        String loginId = principal.getName();

        List<CartDto> cartList = cartService.getCartlistUser(loginId);
//        List<CartDto> cartList0 = cartService.getCartlistBoard();
//        System.out.println(cartList0);


        model.addAttribute("cartList", cartList);


        return "/cart";
    }
//리스트들의 담은것이 카트번호 id,카트 status

    /* 게시글 삭제 */
    @DeleteMapping("/cart/{no}/{boardno}")
    public String delete(@PathVariable("no") Long no, @PathVariable("boardno") Long boardno, Principal principal) {
        cartService.deleteCart(no);


        BoardDto boardDto = boardService.getPost(boardno);


        int sumlike = boardDto.getSumlike();
        if (sumlike >= 1) {
            sumlike = sumlike - 1;
            boardDto.setSumlike(sumlike);
            boardService.savePost(boardDto);
        }


        return "redirect:/cart";
    }


}
