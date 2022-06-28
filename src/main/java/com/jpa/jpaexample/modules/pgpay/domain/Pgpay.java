package com.jpa.jpaexample.modules.pgpay.domain;

import com.jpa.jpaexample.core.domain.BaseEntity;
import com.jpa.jpaexample.core.enums.YN;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Pgpay extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String content;
    private String writer;
    private int hits;

    @Builder
    public Pgpay(Long id, String title, String content, String writer, int hits) {
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