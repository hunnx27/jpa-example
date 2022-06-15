package com.example.jpaexample.modules.board.infrastructure;

import com.example.jpaexample.modules.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
