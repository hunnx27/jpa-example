package com.example.jpaexample.modules.auth.web;

import com.example.jpaexample.core.enums.ErrorCode;
import com.example.jpaexample.core.vo.exception.CustomException;
import com.example.jpaexample.modules.auth.application.util.TokenProvider;
import com.example.jpaexample.modules.auth.web.dto.request.LoginRequest;
import com.example.jpaexample.modules.auth.web.dto.request.SignUpRequest;
import com.example.jpaexample.modules.auth.web.dto.response.ApiResponse;
import com.example.jpaexample.modules.auth.web.dto.response.AuthResponse;
import com.example.jpaexample.modules.user.domain.User;
import com.example.jpaexample.modules.user.domain.enums.AuthProvider;
import com.example.jpaexample.modules.user.domain.enums.Role;
import com.example.jpaexample.modules.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;


    @PostMapping("/login")
    public ResponseEntity<?> authentiateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_IN_USE);
        }

        // 사용자 DB 등록
        User user = User.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .provider(AuthProvider.local)
                .role(Role.USER)
                .build();
        User result = userRepository.save(user);


        // FIXME?? 이부분은 왜있는거지?????
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }

}
