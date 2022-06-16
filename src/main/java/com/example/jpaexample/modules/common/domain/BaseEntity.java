package com.example.jpaexample.modules.common.domain;

import com.example.jpaexample.core.enums.YN;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {
    @CreationTimestamp
    @Column(updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    private ZonedDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    private YN deleteYn = YN.N;
}
