package com.jpa.jpaexample.modules.pgpay.web.dto.request;

import com.jpa.jpaexample.modules.pgpay.domain.Pgpay;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Pgpay 저장시 요청 데이터 받는 객체
 * (!!! 절대 Entity 자체로 요청받으면안됨 !!!)
 */
@Getter
@NoArgsConstructor()
public class PgpaySaveRequestDto {
    private String title;
    private String content;
    private String writer;

    @Builder
    public PgpaySaveRequestDto(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    //사용의 편의를 위한 Domain생성 함수
    public Pgpay toEntity(){
        return Pgpay.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }
}