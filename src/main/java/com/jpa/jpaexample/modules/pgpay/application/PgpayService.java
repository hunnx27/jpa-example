package com.jpa.jpaexample.modules.pgpay.application;

import com.jpa.jpaexample.modules.pgpay.domain.Pgpay;
import com.jpa.jpaexample.modules.pgpay.infra.PgpayRepository;
import com.jpa.jpaexample.modules.pgpay.web.dto.request.PgpaySaveRequestDto;
import com.jpa.jpaexample.modules.pgpay.web.dto.request.PgpayUpdateRequestDto;
import com.jpa.jpaexample.modules.pgpay.web.dto.response.PgpayResponseDto;
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
public class PgpayService {

    private final PgpayRepository pgpayRepository; //@RequiredArgsConstructor으로 Autowired안해도 됨

    // Create
    public PgpayResponseDto save(PgpaySaveRequestDto pgpayDto){
        Pgpay pgpay = pgpayDto.toEntity();
        Pgpay savedPgpay = pgpayRepository.save(pgpay);
        PgpayResponseDto responsePgpay = new PgpayResponseDto(pgpay);
        return responsePgpay;
    }

    // Read(one)
    public PgpayResponseDto findOne(Long id){
        Pgpay pgpay = pgpayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id)); //FIXME CustomException으로 변경해야함.
        PgpayResponseDto responsePgpay = new PgpayResponseDto(pgpay);
        return responsePgpay;
    }

    // Read(Many)
    public List<PgpayResponseDto> findAll(){
        Sort sort = Sort.by(Sort.Direction.DESC, "id", "createdAt");
        List<Pgpay> pgpayList = pgpayRepository.findAll(sort);

        List<PgpayResponseDto> responsePgpaylist =
                                            // 1. stream 객체 이용
                                            // 2. 반복행위(리스트의 모든 pgpay를 PgpayResponseDto로 래핑함)
                                            // 3. 작업된 결과를 List로 뽑음
                pgpayList.stream()
                                            .map(pgpay ->new PgpayResponseDto(pgpay))
                                            .collect(Collectors.toList());
        return responsePgpaylist;
    }

    // Update
    public Long update(Long id, PgpayUpdateRequestDto newPgpay){
        Pgpay oldPgpay = pgpayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id)); //FIXME CustomException으로 변경해야함.

        //entity의 값만 바꿔주면 자동 DB도 함게 수정됨.
        oldPgpay.update(newPgpay.getTitle(), newPgpay.getContent(), newPgpay.getWriter());
        return id;
    }

    // Delete(Hard:물리적 삭제)
    public void deleteHard(Long id){
        Pgpay pgpay = pgpayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id)); //FIXME CustomException으로 변경해야함.
        //pgpayRepository.deleteById(pgpay.getId());
        pgpayRepository.delete(pgpay);
    }

    // Delete(Hard:논리적 삭제)
    public Long deleteSoft(Long id){
        Pgpay oldPgpay = pgpayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+ id)); //FIXME CustomException으로 변경해야함.

        //entity의 값의 deleteYn의 값을 n으로만 바꿔줌.
        oldPgpay.deleteSoft();
        return id;
    }

}
