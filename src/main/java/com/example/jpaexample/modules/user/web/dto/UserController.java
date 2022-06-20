package com.example.jpaexample.modules.user.web.dto;

import com.example.jpaexample.modules.auth.application.exception.ResourceNotFoundException;
import com.example.jpaexample.modules.auth.web.annotation.CurrentUser;
import com.example.jpaexample.modules.auth.web.dto.UserPrincipal;
import com.example.jpaexample.modules.user.appliation.UserService;
import com.example.jpaexample.modules.user.domain.User;
import com.example.jpaexample.modules.user.infra.UserRepository;
import com.example.jpaexample.modules.user.web.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;


    @GetMapping("me")
    @PreAuthorize("hasRole('USER')")
    public UserResponseDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        UserResponseDto response = new UserResponseDto(user);
        return response;
    }


}
