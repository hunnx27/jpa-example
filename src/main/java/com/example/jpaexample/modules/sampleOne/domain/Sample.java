package com.example.jpaexample.modules.sampleOne.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Sample {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
