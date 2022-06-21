package com.jpa.jpaexample.modules.sampleException.web;

import com.jpa.jpaexample.modules.sampleException.application.ExceptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/exception")
public class ExceptionController {

    private final ExceptionService exceptionService;

    @GetMapping("/runtimeException")
    public void runtimeException(){
        exceptionService.runtimeException();
    }
    @PostMapping("/runtimeExceptionPost")
    public void runtimeExceptionPost() {
        exceptionService.runtimeException();
    }

    @GetMapping("/customException")
    public void customException() {
        exceptionService.customException();
    }

    @GetMapping("/exception")
    public void exception() throws Exception {
        exceptionService.exception();
    }

    @GetMapping("/exceptionEmpty")
    public void exceptionEmpty() throws Exception {
        exceptionService.exceptionEmpty();
    }

    @GetMapping("/IllegalArgumentException")
    public void IllegalArgumentException() {
        exceptionService.IllegalArgumentException(1l);
    }

}
