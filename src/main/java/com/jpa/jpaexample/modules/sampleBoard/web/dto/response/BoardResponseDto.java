package com.jpa.jpaexample.modules.sampleBoard.web.dto.response;
import com.jpa.jpaexample.modules.sampleBoard.domain.Board;
import lombok.Getter;

/**
 * Board API 응답 객체
 * (!!! 절대 Entity 자체로 응답하면안됨 !!!)
 */
@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writer;

    public BoardResponseDto(Board entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
    }
}
