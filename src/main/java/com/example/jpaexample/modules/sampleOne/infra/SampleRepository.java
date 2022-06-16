package com.example.jpaexample.modules.sampleOne.infra;

import com.example.jpaexample.modules.sampleOne.domain.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Long> {

}
