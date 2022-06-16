package com.example.jpaexample.modules.user.infra;

import com.example.jpaexample.modules.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
