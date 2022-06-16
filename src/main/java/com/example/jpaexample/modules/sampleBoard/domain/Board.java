package com.example.jpaexample.modules.sampleBoard.domain;

import com.example.jpaexample.core.enums.YN;
import com.example.jpaexample.modules.common.domain.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String content;
    private String writer;
    private int hits;

    @Builder
    public Board(Long id, String title, String content, String writer, int hits) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.hits = hits;
    }

    public void update(String title, String content, String writer) {
        if(title != null) this.title = title;
        if(content != null) this.content = content;
        if(writer != null) this.writer = writer;
    }

    public void deleteSoft(){
        super.setDeleteYn(YN.Y);
    }
}