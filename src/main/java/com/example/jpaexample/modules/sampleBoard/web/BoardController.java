package com.example.jpaexample.modules.sampleBoard.web;

import com.example.jpaexample.modules.sampleBoard.application.BoardService;
import com.example.jpaexample.modules.sampleBoard.web.dto.request.BoardSaveRequestDto;
import com.example.jpaexample.modules.sampleBoard.web.dto.request.BoardUpdateRequestDto;
import com.example.jpaexample.modules.sampleBoard.web.dto.response.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    public BoardResponseDto create(@RequestBody BoardSaveRequestDto newBoard){
        return boardService.save(newBoard);
    }

    @GetMapping("{id}")
    public BoardResponseDto readOne(@PathVariable Long id){
        return boardService.findOne(id);
    }

    @GetMapping("")
    public List<BoardResponseDto> readList(){
        return boardService.findAll();
    }

    @PutMapping("{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto updateBoard){
        return boardService.update(id, updateBoard);
    }

    @DeleteMapping("{id}")
    public void deleteHard(@PathVariable Long id){
        boardService.deleteHard(id);
    }

    @PutMapping("{id}/deleteYn/Y")
    public void deleteSoft(@PathVariable Long id){
        boardService.deleteSoft(id);
    }

}
