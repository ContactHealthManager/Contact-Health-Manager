package com.victolee.board.service;


import com.victolee.board.domain.Role;
import com.victolee.board.domain.entity.BoardEntity;
import com.victolee.board.domain.entity.UserEntity;
import com.victolee.board.domain.repository.UserRepository;
import com.victolee.board.dto.BoardDto;
import com.victolee.board.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;



    /**
     * 회원정보 저장
     *
     * @param infoDto 회원정보가 들어있는 DTO
     * @return 저장되는 회원의 PK
     */
    public String joinUser(UserInfoDto infoDto) { //회원 추가
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        infoDto.setPassword(encoder.encode(infoDto.getPassword()));

        return userRepository.save(infoDto.toEntity()).getId();
    }



    public Optional<UserEntity> findById(String id) {
        return userRepository.findById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //유저정보 불러오기
        Optional<UserEntity> userEntityWrapper = userRepository.findById(username);

        // ... DB 등을 통해 사용자 정보를 셋팅
        UserEntity userEntity = userEntityWrapper.get();
        this.UserinfoEntityToDto(userEntity);

        return userEntity;
    }

    private UserInfoDto UserinfoEntityToDto(UserEntity userEntity) {//나중에 쓸 일 있을것 같아서 유저정보들 객체에 담아놨음
        return UserInfoDto.builder()
                .id(userEntity.getId())
                .password(userEntity.getPassword())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .address(userEntity.getAddress())
                .role(userEntity.getRole())
                .career(userEntity.getCareer())
                .nicname(userEntity.getNicname())
                .build();
    }



    /**
     * Spring Security 필수 메소드 구현
     *
     * @param id 아이디
     * @return UserDetails
     * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
     */
     // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 User로 반환 타입 지정 (자동으로 다운 캐스팅됨)

    public UserEntity checkUserid(String id) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException((id))); // 이건 id 중복일때  새로운 유저 id 생성 x
    }
}
