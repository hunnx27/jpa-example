package com.example.jpaexample.modules.sampleException.application;

import com.example.jpaexample.core.enums.ErrorCode;
import com.example.jpaexample.core.vo.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ExceptionService {

    public void runtimeException(){
        throw new RuntimeException("Holy! Runtime Exception!");
    }

    public void exception() throws Exception{
        throw new Exception("Holy! Exception!");
    }

    public void exceptionEmpty() throws Exception{
        throw new Exception();
    }

    public void customException(){
        throw new CustomException(ErrorCode.CANNOT_FOLLOW_MYSELF);
    }

    public void IllegalArgumentException(Long id){
        throw new IllegalArgumentException("해당 게시글이 없습니다. id="+ id);
    }

}
