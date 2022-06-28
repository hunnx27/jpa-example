package com.jpa.jpaexample.modules.pgpay.web.dto.response;
import com.jpa.jpaexample.modules.pgpay.domain.Pgpay;
import lombok.Getter;

/**
 * Pgpay API 응답 객체
 * (!!! 절대 Entity 자체로 응답하면안됨 !!!)
 */
@Getter
public class PgpayResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writer;

    public PgpayResponseDto(Pgpay entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
    }
}
