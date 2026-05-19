package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByTokenAndIsUsedFalse(String token);

}
