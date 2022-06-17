package com.example.jpaexample.config;

import com.example.jpaexample.modules.auth.application.CustomOAuth2UserService;
import com.example.jpaexample.modules.auth.web.JwtAuthenticationExceptionEntryPoint;
import com.example.jpaexample.modules.auth.web.JwtAuthenticationFilter;
import com.example.jpaexample.modules.user.domain.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableWebMvc
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAuthenticationExceptionEntryPoint jwtExcpetionEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
            // Statelestt 설정
            .cors()// CORS허용
            .and()
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .csrf().disable()//CSRF보안 취약점 해제(사용 복잡도 때문에?)
            .formLogin().disable()//폼로그인 해제
            .headers().frameOptions().disable() //동일 도메인 Iframe접근 허용

            // 인가 설정
            .and()
                .authorizeHttpRequests()
                .mvcMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**")
                    .permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/**").hasRole(Role.USER.name())
                        .anyRequest().authenticated()
            // JWT 인증 설정
            .and()
                .exceptionHandling().authenticationEntryPoint(jwtExcpetionEntryPoint) //오류설정

            // OAuth2 설정
            .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

        // 시큐리티 기본 패스워드인증 필터전 JWT토큰 요청 파싱 필터 추가
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // 비밀번호 암호화를 위한 Encoder 설정
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
