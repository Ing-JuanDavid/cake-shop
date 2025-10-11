package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.RateDto;
import com.juan.cakeshop.api.dto.responses.RateResponse;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface RateService {


    RateResponse createRate(String email, int productId, RateDto rateDto);

    List<RateResponse> getRatesByProduct(int productId);

    RateResponse updateRate(String email, int productId, int rateId, RateDto rateDto);

    RateResponse deleteRate(Authentication authentication, int rateId);
}
