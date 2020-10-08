package com.victolee.board.controller;

import com.victolee.board.dto.MailDto;
import com.victolee.board.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class ServiceCenterController {



        @GetMapping("/servicecenter")
        public String servicecenter() {
            return "servicecenter";
        }




}
