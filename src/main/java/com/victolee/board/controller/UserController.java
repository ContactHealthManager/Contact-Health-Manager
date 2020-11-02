package com.victolee.board.controller;


import com.victolee.board.domain.entity.UserEntity;
import com.victolee.board.domain.repository.UserRepository;
import com.victolee.board.dto.BoardDto;

import com.victolee.board.dto.UserInfoDto;
import com.victolee.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;


@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;


    @PostMapping("/user")
    public String signup(UserInfoDto infoDto) { // 회원 추가
        userService.joinUser(infoDto);

        return "redirect:/login";
    }


    @GetMapping(value = "/logout")//로그아웃 처리
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    @RequestMapping("/myinfo") //로그인한 정보를 내정보에 불러오기
    public ModelAndView myinfo(@AuthenticationPrincipal UserEntity userEntity) {
        ModelAndView view = new ModelAndView();
        view.setViewName("/myinfo");

        String username = userEntity.getId();
        String phone = userEntity.getPhone();
        String email = userEntity.getEmail();
        String address = userEntity.getAddress();
        String role = userEntity.getRole();
        String career = userEntity.getCareer();
        String nicname = userEntity.getNicname();

        view.addObject("id",username);
        view.addObject("phone",phone);
        view.addObject("email",email);
        view.addObject("address",address);
        view.addObject("role",role);
        view.addObject("career",career);
        view.addObject("nicname",nicname);


        return view;

    }



}
