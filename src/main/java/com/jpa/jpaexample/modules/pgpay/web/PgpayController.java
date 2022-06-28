package com.jpa.jpaexample.modules.pgpay.web;

import com.jpa.jpaexample.modules.pgpay.application.PgpayService;
import com.jpa.jpaexample.modules.pgpay.web.dto.request.PgpaySaveRequestDto;
import com.jpa.jpaexample.modules.pgpay.web.dto.request.PgpayUpdateRequestDto;
import com.jpa.jpaexample.modules.pgpay.web.dto.response.PgpayResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/pgpay")
public class PgpayController {

    private final PgpayService pgpayService;

    @PostMapping("")
    public PgpayResponseDto create(@RequestBody PgpaySaveRequestDto newPgpay){
        return pgpayService.save(newPgpay);
    }

    @GetMapping("{id}")
    public PgpayResponseDto readOne(@PathVariable Long id){
        return pgpayService.findOne(id);
    }

    @GetMapping("")
    public List<PgpayResponseDto> readList(){
        return pgpayService.findAll();
    }

    @PutMapping("{id}")
    public Long update(@PathVariable Long id, @RequestBody PgpayUpdateRequestDto updatePgpay){
        return pgpayService.update(id, updatePgpay);
    }

    @DeleteMapping("{id}")
    public void deleteHard(@PathVariable Long id){
        pgpayService.deleteHard(id);
    }

    @PutMapping("{id}/deleteYn/Y")
    public void deleteSoft(@PathVariable Long id){
        pgpayService.deleteSoft(id);
    }

}
