package com.example.jpaexample.core.web.filter;

import com.example.jpaexample.core.enums.ErrorCode;
import com.example.jpaexample.core.vo.exception.CustomException;
import com.example.jpaexample.core.vo.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class ExceptionFilterHandler extends OncePerRequestFilter {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Spring Security Filter Chain Exception:", e);
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, e);
        }

    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex){
        response.setStatus(status.value());
        response.setContentType("application/json");
        ErrorResponse errorResponse;
        if(ex instanceof CustomException){
            errorResponse = new ErrorResponse(((CustomException) ex).getErrorCode());
        }else {
            errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());
        }

        try{
            String json = errorResponse.convertToJSON();
            log.error(json);
            byte[] body = json.getBytes(StandardCharsets.UTF_8);
            response.getOutputStream().write(body);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
