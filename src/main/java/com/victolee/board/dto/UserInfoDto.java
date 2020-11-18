package com.victolee.board.dto;

import com.victolee.board.domain.entity.UserEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserInfoDto {
    //유저 아이디
    private String id;
    //유저 비밀번호
    private String password;
    //유저 전화번호
    private String phone;
    //유저 이메일
    private String email;
    //유저 주소
    private String address;
    //유저 역활
    private String role;
    //유저 경력
    private String career;
    //유저 닉네임
    private String nicname;

    private String token;

    public UserEntity toEntity(){
        return UserEntity.builder()
                .id(id)
                .password(password)
                .phone(phone)
                .email(email)
                .address(address)
                .role(role)
                .career(career)
                .nicname(nicname)
                .build();
    }

    @Builder
    public UserInfoDto(String id,String password,String phone,String email,String address
            ,String role,String career,String nicname,String token){
        this.id =id;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
        this.career = career;
        this.nicname = nicname;
        this.token=token;
    }

}