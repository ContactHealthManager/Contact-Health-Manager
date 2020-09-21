package com.victolee.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private String id;
    private String email;
    private String password;
    private String phone;
    private String auth;
}