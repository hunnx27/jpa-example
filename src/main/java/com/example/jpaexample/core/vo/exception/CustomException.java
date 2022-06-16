package com.example.jpaexample.core.vo.exception;

import com.example.jpaexample.core.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{//RuntimeException을 상속해 Uncheck 예외로 정의함(불필요한 throw전파를 막는다)
    private final ErrorCode errorCode;
}
