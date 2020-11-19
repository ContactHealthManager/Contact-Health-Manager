package com.victolee.board.controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class CommunityController {



    @GetMapping("/community")
    public String community() {
        return "community";
    }




}
