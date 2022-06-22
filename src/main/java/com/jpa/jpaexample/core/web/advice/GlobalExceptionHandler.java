package com.jpa.jpaexample.core.web.advice;

import com.jpa.jpaexample.core.enums.ErrorCode;
import com.jpa.jpaexample.core.vo.exception.CustomException;
import com.jpa.jpaexample.core.vo.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 사용자 정의 Exception
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
//        return new ErrorResponse(e.getErrorCode());
    }

    /**
     *
     * Exception들 추가 필요...
     * ...
     * ...
     * ...+
     */
    /**
     *  InternalAuthenticationServiceException 인증 Exception
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ErrorResponse> internalAuthenticationServiceException(InternalAuthenticationServiceException e, HttpServletRequest request){
        ResponseEntity<ErrorResponse> response;
        // Default 처리
        ErrorCode errorCode = ErrorCode.INVALID_AUTH_TOKEN;
        response = ErrorResponse.toResponseEntity(errorCode);

        // CustomException인 경우 처리
        if(e.getCause() instanceof CustomException){
            CustomException ce = (CustomException) e.getCause();
            response = ErrorResponse.toResponseEntity(ce.getErrorCode(), ce.getArgs());
        }
        return response;
    }


    /**
     * @valid  유효성체크에 통과하지 못하면  MethodArgumentNotValidException 이 발생한다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        log.warn("MethodArgumentNotValidException 발생!!! url:{}, trace:{}",request.getRequestURI(), e.getStackTrace());
        BindingResult bindingResult = e.getBindingResult();
        String detail = bindingResult.getFieldError().getDefaultMessage();

        ErrorCode errorCdoe = ErrorCode.METHOD_ARGUMENT_NOT_VALID;

        return ErrorResponse.toResponseEntity(errorCdoe, new String[]{detail});
    }


    /**
     * 공통 Exception
     * @param ex
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex) {
        log.warn("handleAllException", ex);
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        if(ex.getMessage()!=null){
            return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR_DETAIL, ex.getMessage());
        }else {
            return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

}
