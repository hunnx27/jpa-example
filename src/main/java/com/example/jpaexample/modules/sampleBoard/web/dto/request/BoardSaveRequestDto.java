package com.example.jpaexample.modules.sampleBoard.web.dto.request;

import com.example.jpaexample.modules.sampleBoard.domain.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Board 저장시 요청 데이터 받는 객체
 * (!!! 절대 Entity 자체로 요청받으면안됨 !!!)
 */
@Getter
@NoArgsConstructor()
public class BoardSaveRequestDto {
    private String title;
    private String content;
    private String writer;

    @Builder
    public BoardSaveRequestDto(String title, String content, String writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    //사용의 편의를 위한 Domain생성 함수
    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }
}