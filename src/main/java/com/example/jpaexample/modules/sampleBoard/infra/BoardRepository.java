package com.example.jpaexample.modules.sampleBoard.infra;

import com.example.jpaexample.modules.sampleBoard.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
