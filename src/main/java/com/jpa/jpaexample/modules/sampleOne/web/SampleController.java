package com.jpa.jpaexample.modules.sampleOne.web;

import com.jpa.jpaexample.modules.sampleOne.appliation.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/samples")
public class SampleController {

    private final SampleService sampleService;

}
