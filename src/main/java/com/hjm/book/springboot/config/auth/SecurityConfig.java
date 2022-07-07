package com.hjm.book.springboot.config.auth;

import com.hjm.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // h2-console 화면 사용하기 위해 해당 옵션들 disable
                .headers().frameOptions().disable() // h2-console 화면 사용하기 위해 해당 옵션들 disable
                .and()
                    .authorizeRequests() // URL별 권한 관리 설정 옵션의 시작점, 이 메소드가 선언되야만 antMatchers 옵션 사용 가능
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() // permitAll() 옵션을 통해 전체 열람 권한
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // andMatchers : 권한 관리 대상을 지정하는 옵션
                    .anyRequest().authenticated() // 설정된 값들 이외 나머지 URL들은 인증된(로그인된) 사용자들에게만 허용
                .and()
                    .logout()
                        .logoutSuccessUrl("/") // 로그아웃 기능 설정 진입점
                .and()
                    .oauth2Login() // 로그인 기능 설정 진입점
                        .userInfoEndpoint() // 로그인 성공시 사용자 정보를 가져올 때의 설정들
                            .userService(customOAuth2UserService); // 소셜 로그인 성공시 후속 조치를 진행할 UserService 인터페이스 구현체 등록
    }
}
