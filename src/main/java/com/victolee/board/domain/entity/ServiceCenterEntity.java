package com.victolee.board.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity

@Table(name = "servicecenter")
public class ServiceCenterEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String user;


    @Builder
    public ServiceCenterEntity(Long id, String title, String content,String email,String user) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.email = email;
        this.user = user;

    }
}
