package com.example.jpaexample.modules.user.web.dto;

import com.example.jpaexample.modules.sampleBoard.application.BoardService;
import com.example.jpaexample.modules.sampleBoard.web.dto.request.BoardSaveRequestDto;
import com.example.jpaexample.modules.sampleBoard.web.dto.request.BoardUpdateRequestDto;
import com.example.jpaexample.modules.sampleBoard.web.dto.response.BoardResponseDto;
import com.example.jpaexample.modules.user.appliation.UserService;
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

}
