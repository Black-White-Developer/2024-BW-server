package com.github.cokothon.domain.auth.repository;

import com.github.cokothon.domain.auth.schema.VerifyCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifyCodeRepository extends CrudRepository<VerifyCode, Long> {

    Optional<VerifyCode> findByEmail(String email);
}
