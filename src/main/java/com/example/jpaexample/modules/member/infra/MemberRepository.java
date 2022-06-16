package com.example.jpaexample.modules.member.infra;

import com.example.jpaexample.modules.member.domain.Member;
import com.example.jpaexample.modules.sampleBoard.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
