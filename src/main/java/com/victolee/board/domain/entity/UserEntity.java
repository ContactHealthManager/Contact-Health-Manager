package com.victolee.board.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "u_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long u_id;


    @Column
    private String id;

    @Column
    private String password;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private String address;

    @Column
    private String role;

    @Column
    private String carrer;

    @Column
    private String u_name;

    @Column
    private String token;

    @Builder
    public UserEntity(Long u_id, String id, String password,String phone, String email, String address,String role, String carrer, String u_name, String token) {
        this.u_id = u_id;
        this.id = id;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.role = role;
        this.carrer = carrer;
        this.u_name = u_name;
        this.token = token;

    }

    // 사용자의 권한을 콜렉션 형태로 반환
    // 단, 클래스 자료형은 GrantedAuthority를 구현해야함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();

        for (String role : role.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }

        return roles;
    }

    // 사용자의 id를 반환 (unique한 값)
    @Override
    public String getUsername() {
        return id;
    }

    // 사용자의 password를 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}
