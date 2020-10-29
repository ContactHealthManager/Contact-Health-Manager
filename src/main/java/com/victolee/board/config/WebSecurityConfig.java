package com.victolee.board.config;

import com.victolee.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@RequiredArgsConstructor
@EnableWebSecurity // 1
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // 2

    private final UserService userService; // 3


    @Bean
    public PasswordEncoder passwordEncoder() { // 4
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) { // 5
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // 6
        http
                .authorizeRequests() // 접근에 대한 인증 설정
                .antMatchers("/login","/signup","/user").permitAll() // 누구나 접근 허용
                .antMatchers("/post").hasRole("TRAINER")
//                .antMatchers("/chat/**").hasRole("TRAINER")
//                .antMatchers("/chat/**").hasRole("HELLIN")
                .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
                .and()
                .headers()
                .frameOptions().sameOrigin() // SockJS는 기본적으로 HTML iframe 요소를 통한 전송을 허용하지 않도록 설정되는데 해당 내용을 해
                .and()
                .formLogin() // 로그인에 관한 설정
                .loginPage("/login") // 로그인 페이지 링크
                .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 주소
                .and()
                .logout() // 로그아웃
                .logoutSuccessUrl("/") // 로그아웃 성공시 리다이렉트 주소
                .invalidateHttpSession(true) // 세션 날리기
                .and()
                .csrf().disable();

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { // 10
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder()); // 해당 서비스(userService)에서는 UserDetailsService를 implements해서 loadUserByUsername() 구현해야함 (서비스 참고)

    }
}