package com.jpa.jpaexample.modules.user.web.dto.response;

import com.jpa.jpaexample.modules.user.domain.User;
import com.jpa.jpaexample.modules.user.domain.enums.AuthProvider;
import com.jpa.jpaexample.modules.user.domain.enums.Role;
import lombok.Getter;

/**
 * Meber API 응답 객체
 * (!!! 절대 Entity 자체로 응답하면안됨 !!!)
 */
@Getter
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private AuthProvider provider;
    private Role role;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.provider = user.getProvider();
        this.role = user.getRole();
    }
}
