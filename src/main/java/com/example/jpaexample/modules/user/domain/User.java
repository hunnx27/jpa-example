package com.example.jpaexample.modules.user.domain;

import com.example.jpaexample.modules.user.domain.enums.AuthProvider;
import com.example.jpaexample.modules.common.domain.BaseEntity;
import com.example.jpaexample.modules.user.domain.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "account") //FIXME 임시 테이블명
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable =false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;
    private String providerId;

    @Builder
    public User(Long id, String name, String email, String password, String picture, Role role, AuthProvider provider, String providerId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public User update(String name, String picture){
        this.name = name;
        this.picture = picture;
        return this;
    }

//    public String getRoleKey(){
//        return this.role.getKey();
//    }
}
