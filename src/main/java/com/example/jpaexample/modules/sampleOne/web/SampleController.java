package com.example.jpaexample.modules.sampleOne.web;

import com.example.jpaexample.modules.sampleBoard.application.BoardService;
import com.example.jpaexample.modules.sampleBoard.web.dto.request.BoardSaveRequestDto;
import com.example.jpaexample.modules.sampleBoard.web.dto.request.BoardUpdateRequestDto;
import com.example.jpaexample.modules.sampleBoard.web.dto.response.BoardResponseDto;
import com.example.jpaexample.modules.sampleOne.appliation.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/samples")
public class SampleController {

    private final SampleService sampleService;

}
