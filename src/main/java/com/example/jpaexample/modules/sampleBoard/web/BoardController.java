package com.example.jpaexample.modules.sampleBoard.web;

import com.example.jpaexample.common.vo.exception.CustomException;
import com.example.jpaexample.common.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/board")
public class BoardController {


    @GetMapping("/test")
    public String testGet(){
        throw new RuntimeException("Holy! Runtime Exception!");
    }
    @PostMapping("/test")
    public String test(){
        throw new RuntimeException("Holy! Runtime Exception!");
    }

    @GetMapping("/exception")
    public String exception() throws Exception {
        throw new Exception("Holy! Exception!");
    }

    @GetMapping("/customException")
    public String customException() throws CustomException {
        throw new CustomException(ErrorCode.CANNOT_FOLLOW_MYSELF);
    }

}
