package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.model.Rate;
import com.juan.cakeshop.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Integer> {
    Optional<Rate> findByUserAndRateId(User user, int rateId);
}