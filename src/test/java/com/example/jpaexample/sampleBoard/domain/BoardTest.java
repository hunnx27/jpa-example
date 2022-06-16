package com.example.jpaexample.sampleBoard.domain;

import com.example.jpaexample.modules.sampleBoard.domain.Board;
import com.example.jpaexample.modules.sampleBoard.infra.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardTest {

    @Autowired
    BoardRepository boardRepository;

    @Test
    void save(){
        // 1. 게시글 파라미터 생성
        Board params = Board.builder()
                .title("1번글 입력")
                .content("게시글을 입력했다")
                .writer("아이언맨")
                .hits(0)
                .build();

        // 2. 게시글 저장
        boardRepository.save(params);

        // 3. 1번 게시글 정보 조회
        Board entity = boardRepository.findById(1l).get();
        assertEquals(entity.getTitle(), "1번글 입력");
        //assertEquals(params, entity);
    }

    @Test
    void findAll(){
        // 1. 전체 게시글 수 조회
        long boardCount = boardRepository.count();

        // 2. 전체 게시글 리스트 조회
        List<Board> boards = boardRepository.findAll();
    }

    @Test
    void delete(){
        // 1. 게시글 조회
        Board entity = boardRepository.findById(1l).get();

        // 2. 게시글 삭제
        boardRepository.delete(entity);
    }

}