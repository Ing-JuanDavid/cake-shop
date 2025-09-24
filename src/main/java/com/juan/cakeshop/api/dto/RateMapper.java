package com.juan.cakeshop.api.dto;

import com.juan.cakeshop.api.dto.requests.RateDto;
import com.juan.cakeshop.api.dto.responses.RateResponse;
import com.juan.cakeshop.api.model.Product;
import com.juan.cakeshop.api.model.Rate;
import com.juan.cakeshop.api.model.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateMapper {
    public Rate toEntity(RateDto rateDto, User user, Product product)
    {
        return Rate.builder()
                .score(rateDto.getScore())
                .comment(rateDto.getComment())
                .user(user)
                .product(product)
                .date(LocalDate.now()).build();
    }

    public RateResponse toResponse(Rate rate)
    {
        return RateResponse.builder()
                .rateId(rate.getRateId())
                .userName(rate.getUser().getName())
                .score(rate.getScore())
                .comment(rate.getComment())
                .productId(rate.getProduct().getProductId())
                .productName(rate.getProduct().getName())
                .creationDate(rate.getDate())
                .build();
    }

    public List<RateResponse> toList(List<Rate> rates) {
        return rates.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Rate updateFromRateDto(Rate savedRate, RateDto rateDto) {

        if(rateDto.getScore() != null) savedRate.setScore(rateDto.getScore());
        if(rateDto.getComment() != null && !rateDto.getComment().isBlank()) savedRate.setComment(rateDto.getComment());
        savedRate.setDate(LocalDate.now());
        return  savedRate;
    }
}
