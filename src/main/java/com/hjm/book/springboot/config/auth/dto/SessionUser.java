package com.hjm.book.springboot.config.auth.dto;

import com.hjm.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter // 직력화 기능을 가진 세션 Dto - 운영 및 유지보수에 도움
public class SessionUser implements Serializable { // 인증된 사용자 정보만 필요
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
