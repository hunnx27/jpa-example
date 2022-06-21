package com.jpa.jpaexample.modules.sampleOne.infra;

import com.jpa.jpaexample.modules.sampleOne.domain.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Long> {

}
