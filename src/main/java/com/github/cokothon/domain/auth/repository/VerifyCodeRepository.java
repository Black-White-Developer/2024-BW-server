package com.github.cokothon.domain.auth.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.cokothon.domain.auth.schema.VerifyCode;

@Repository
public interface VerifyCodeRepository extends CrudRepository<VerifyCode, Long> {

	Optional<VerifyCode> findByEmail(String email);
}
