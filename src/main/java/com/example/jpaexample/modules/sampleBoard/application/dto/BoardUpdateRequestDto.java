package com.example.jpaexample.modules.sampleBoard.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Board 수정시 요청 데이터 받는 객체
 * (!!! 절대 Entity 자체로 요청받으면안됨 !!!)
 */
@Getter
@NoArgsConstructor()
public class BoardUpdateRequestDto {
    private String title;
    private String content;
    private String writer;

    @Builder
    public BoardUpdateRequestDto(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}