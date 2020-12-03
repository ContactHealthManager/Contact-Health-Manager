package com.victolee.board.dto;

import com.victolee.board.domain.entity.ServiceCenterEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ServiceCenterDto {
    // 게시글 고유번호
    private Long id;

    // 글 제목
    private String title;

    // 글 내용
    private String content;

    // 이메일
    private String email;

    // 유저 고유번호
    private String user;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;



    public ServiceCenterEntity toEntity() { //글쓰기 저장을 위한 엔티티
        ServiceCenterEntity serviceCenterEntity = ServiceCenterEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .email(email)
                .user(user)
                .build();
        return serviceCenterEntity;
    }

    @Builder
    private ServiceCenterDto(Long id , String title, String content,String email,String user,LocalDateTime createdDate,LocalDateTime modifiedDate) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.email = email;
        this.user = user;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;

    }


}
