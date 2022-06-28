package com.jpa.jpaexample.modules.pgpay.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Pgpay 수정시 요청 데이터 받는 객체
 * (!!! 절대 Entity 자체로 요청받으면안됨 !!!)
 */
@Getter
@NoArgsConstructor()
public class PgpayUpdateRequestDto {
    private String title;
    private String content;
    private String writer;

    @Builder
    public PgpayUpdateRequestDto(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}