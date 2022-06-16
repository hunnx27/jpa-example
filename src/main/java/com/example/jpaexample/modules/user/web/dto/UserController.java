package com.example.jpaexample.modules.user.web.dto;

import com.example.jpaexample.modules.sampleBoard.application.BoardService;
import com.example.jpaexample.modules.sampleBoard.web.dto.request.BoardSaveRequestDto;
import com.example.jpaexample.modules.sampleBoard.web.dto.request.BoardUpdateRequestDto;
import com.example.jpaexample.modules.sampleBoard.web.dto.response.BoardResponseDto;
import com.example.jpaexample.modules.user.appliation.UserService;
import com.example.jpaexample.modules.user.domain.User;
import com.example.jpaexample.modules.user.domain.enums.Role;
import com.example.jpaexample.modules.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("createSample")
    private void createSampleData(){
        //User user = U
        User user = User.builder()
                .email("hunnx27@gmail.com")
                .name("최지훈")
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }
    @GetMapping("test")
    private Object test(){
        return userRepository.findByEmail("hunnx27@gmail.com");
    }


}
