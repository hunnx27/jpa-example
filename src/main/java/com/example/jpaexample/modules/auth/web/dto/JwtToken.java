package com.example.jpaexample.modules.auth.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class JwtToken {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Reqeust{
        private String id;
        private String secret;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Response{
        private String token;
    }
}
