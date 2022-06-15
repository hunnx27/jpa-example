package com.example.jpaexample.modules.board.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String content;
    private String writer;
    private int hits;
    private char deleteYn='N';
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifiedDate;

    @Builder
    public Board(Long id, String title, String content, String writer, int hits, char deleteYn) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.hits = hits;
        this.deleteYn = deleteYn;
    }

    public void update(String title, String content, String writer) {
        if(title != null) this.title = title;
        if(content != null) this.content = content;
        if(writer != null) this.writer = writer;
        this.modifiedDate = LocalDateTime.now();
    }

    public void deleteSoft(){
        this.deleteYn = 'Y';
    }
}