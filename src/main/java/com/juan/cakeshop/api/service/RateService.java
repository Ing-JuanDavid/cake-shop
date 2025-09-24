package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.RateDto;
import com.juan.cakeshop.api.dto.responses.RateResponse;

import java.util.List;

public interface RateService {


    RateResponse createRate(int productId, RateDto rateDto);

    List<RateResponse> getRatesByProduct(int productId);

    RateResponse updateRate(int productId, int rateId, RateDto rateDto);

    RateResponse deleteRate(int rateId);
}
