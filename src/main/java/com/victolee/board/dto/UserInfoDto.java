package com.victolee.board.dto;

import com.victolee.board.domain.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

public class UserInfoDto {

    private String id;
    private String password;
    private String phone;
    private String email;
    private String address;
    private String role;
    private String career;
    private String nicname;

    public UserEntity toEntity() { //글쓰기 저장을 위한 엔티티
        UserEntity userEntity = UserEntity.builder()
                .id(id)
                .password(password)
                .phone(phone)
                .email(email)
                .address(address)
                .role(role)
                .career(career)
                .nicname(nicname)

                .build();
        return userEntity;
    }
    @Builder
    public UserInfoDto(String id, String password, String phone,
            String email,String address,String role,String career, String nicname) {

            this.id = id;
            this.password = password;
            this.phone = phone;
            this.email = email;
            this.address = address;
            this.role = role;
            this.career = career;
            this.nicname = nicname;

        }
    }

