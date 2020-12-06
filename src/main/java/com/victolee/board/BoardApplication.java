package com.victolee.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
    }
    @Bean(name = "uploadPath")
    public String uploadPath() {

        return "/home/ec2-user/apps/spring_practice/src/main/resources/static/images/summernote";

    }
}
