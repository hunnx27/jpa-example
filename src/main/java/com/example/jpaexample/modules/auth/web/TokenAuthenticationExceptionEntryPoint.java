package com.example.jpaexample.modules.auth.web;

import com.example.jpaexample.core.enums.ErrorCode;
import com.example.jpaexample.core.vo.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * JWT Authentication Failed Exception Handler
 */
@Slf4j
@Component
public class TokenAuthenticationExceptionEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//        log.error("Responding with unauthorized error. Message - {}", e.getMessage());
//        //ErrorCode unAuthorizationCode = (ErrorCode) request.getAttribute("unauthorization.code");
//        //request.setAttribute("response.failure.code", unAuthorizationCode.name());
//        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, unAuthorizationCode.getDetail());
        throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
//        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());

//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//        String message;
//
//        if (e.getCause() != null) {
//            message = e.getCause().toString() + " " + e.getMessage();
//        } else {
//            message = e.getMessage();
//        }
//
//        byte[] body = new ObjectMapper().writeValueAsBytes(Collections.singletonMap("error", message));
//
//        response.getOutputStream().write(body);
    }
}
