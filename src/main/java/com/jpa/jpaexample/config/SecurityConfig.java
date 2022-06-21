package com.jpa.jpaexample.config;

import com.jpa.jpaexample.core.web.filter.ExceptionFilterHandler;
import com.jpa.jpaexample.modules.auth.application.CustomOAuth2UserDetailsService;
import com.jpa.jpaexample.modules.auth.application.CustomUserDetailsService;
import com.jpa.jpaexample.modules.auth.infra.HttpCookieOAuth2AuthorizationRequestRepository;
//import com.example.jpaexample.modules.auth.web.JwtAuthenticationExceptionEntryPoint;
//import com.example.jpaexample.modules.auth.web.JwtAuthenticationFilter;
import com.jpa.jpaexample.modules.auth.web.*;
import com.jpa.jpaexample.modules.user.domain.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RequiredArgsConstructor
@Configuration
@EnableWebMvc
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuth2UserDetailsService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    // JWT 토큰 검증 필터
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }

    //OAuth2 쿠키 저장 설정
    /*
      By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
      the authorization request. But, since our service is stateless, we can't save it in
      the session. We'll save the request in a Base64 encoded cookie instead.
    */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    // 비밀번호 암호화를 위한 Encoder 설정
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {

        // 기본 AuthenticationManager와 UserDetailService 생성
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();


        http
             // stateless 설정
            .cors()// CORS허용
            .and()
            .httpBasic()
            .disable()
            .authenticationManager(authenticationManager)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf().disable()//CSRF보안 취약점 해제(사용 복잡도 때문에?)
            .formLogin().disable()//폼로그인 해제
            .headers().frameOptions().disable() //동일 도메인 Iframe접근 허용

            // 인가 설정
            .and()
                .authorizeHttpRequests()
                .antMatchers("/",
                        "/error",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                .antMatchers("/api/auth/**", "/api/oauth2/**")
                .permitAll()
                .antMatchers("/api/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
            // JWT토큰 Exception 설정
            .and()
                .exceptionHandling().authenticationEntryPoint(new TokenAuthenticationExceptionEntryPoint()) //Exception처리 설정
            // OAuth2 설정
            .and()
                .oauth2Login()
                .authorizationEndpoint()
                    .baseUri("/oauth2/authorize") //OAuth 요청시 처리 설정
                    .authorizationRequestRepository(cookieAuthorizationRequestRepository())
                    .and()
                .redirectionEndpoint()
                    .baseUri("/oauth2/callback/*") // OAuth 응답시 처리 설정
                    .and()
                .userInfoEndpoint()
                    .userService(customOAuth2UserService) //OAuth UserDetail처리시 설정
                    .and()
                .successHandler(oAuth2AuthenticationSuccessHandler) //OAuth 성공시 핸들링 설정
                .failureHandler(oAuth2AuthenticationFailureHandler) //OAuth 실패시 핸들링 설정
        ;


        // 시큐리티 기본 패스워드인증 필터전 JWT토큰 요청 파싱 필터 추가
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new ExceptionFilterHandler(), TokenAuthenticationFilter.class);
        return http.build();
    }

}
