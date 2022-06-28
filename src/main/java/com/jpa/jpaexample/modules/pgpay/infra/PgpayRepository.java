package com.jpa.jpaexample.modules.pgpay.infra;

import com.jpa.jpaexample.modules.pgpay.domain.Pgpay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PgpayRepository extends JpaRepository<Pgpay, Long> {

}
