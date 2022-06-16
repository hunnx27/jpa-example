package com.example.jpaexample.modules.sampleBoard.application;

import com.example.jpaexample.modules.sampleBoard.web.dto.response.BoardResponseDto;
import com.example.jpaexample.modules.sampleBoard.web.dto.request.BoardSaveRequestDto;
import com.example.jpaexample.modules.sampleBoard.web.dto.request.BoardUpdateRequestDto;
import com.example.jpaexample.modules.sampleBoard.domain.Board;
import com.example.jpaexample.modules.sampleBoard.infra.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository; //@RequiredArgsConstructor으로 Autowired안해도 됨

    // Create
    public BoardResponseDto save(BoardSaveRequestDto boardDto){
        Board board = boardDto.toEntity();
        Board savedBoard = boardRepository.save(board);
        log.info(Long.toString(savedBoard.getId()));
        boardRepository.flush();//DB에 저장(그전까지는 캐시 물고 있음, ID는 디비 저장되고 주기 때문에 플러시 후 받아야함)
        log.info(Long.toString(savedBoard.getId()));
        BoardResponseDto responseBoard = new BoardResponseDto(savedBoard);
        return responseBoard;
    }

    // Read(one)
    public BoardResponseDto findOne(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id)); //FIXME CustomException으로 변경해야함.
        BoardResponseDto responseBoard = new BoardResponseDto(board);
        return responseBoard;
    }

    // Read(Many)
    public List<BoardResponseDto> findAll(){
        Sort sort = Sort.by(Sort.Direction.DESC, "id", "createdAt");
        List<Board> boardList = boardRepository.findAll(sort);

        List<BoardResponseDto> responseBoardlist =
                                            // 1. stream 객체 이용
                                            // 2. 반복행위(리스트의 모든 board를 BoardResponseDto로 래핑함)
                                            // 3. 작업된 결과를 List로 뽑음
                                            boardList.stream()
                                            .map(board -> new BoardResponseDto(board))
                                            .collect(Collectors.toList());
        return responseBoardlist;
    }

    // Update
    public Long update(Long id, BoardUpdateRequestDto newBoard){
        Board oldBoard = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id)); //FIXME CustomException으로 변경해야함.

        //entity의 값만 바꿔주면 자동 DB도 함게 수정됨.
        oldBoard.update(newBoard.getTitle(), newBoard.getContent(), newBoard.getWriter());
        return id;
    }

    // Delete(Hard:물리적 삭제)
    public void deleteHard(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id)); //FIXME CustomException으로 변경해야함.
        //boardRepository.deleteById(board.getId());
        boardRepository.delete(board);
    }

    // Delete(Hard:논리적 삭제)
    public Long deleteSoft(Long id){
        Board oldBoard = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id)); //FIXME CustomException으로 변경해야함.

        //entity의 값의 deleteYn의 값을 n으로만 바꿔줌.
        oldBoard.deleteSoft();
        return id;
    }

}
