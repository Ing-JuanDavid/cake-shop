package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.RateDto;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.dto.responses.RateResponse;
import com.juan.cakeshop.api.service.RateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @PostMapping("/products/{productId}/rates")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<RateResponse>> createRate(
            @AuthenticationPrincipal String email,
            @PathVariable int productId,
            @RequestBody @Valid RateDto rateDto)
    {
        return ResponseEntity.ok(GenericResponse.<RateResponse>builder()
                .message("ok")
                .data(rateService.createRate(email, productId, rateDto))
                .build());
    }

    @GetMapping("/products/{productId}/rates")
    public ResponseEntity<GenericResponse<List<RateResponse>>> getRatesByProduct(@PathVariable int productId)
    {
        return ResponseEntity.ok(GenericResponse.<List<RateResponse>>builder()
                .message("ok")
                .data(rateService.getRatesByProduct(productId))
                .build());
    }

    @PutMapping("/products/{productId}/rates/{rateId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<RateResponse>> updateRate(
            @AuthenticationPrincipal String email,
            @PathVariable int productId,
            @PathVariable int rateId,
            @RequestBody @Valid RateDto rateDto)
    {
        return ResponseEntity.ok(GenericResponse.<RateResponse>builder()
                .message("ok")
                .data(rateService.updateRate(email, productId, rateId, rateDto))
                .build());
    }

    @DeleteMapping("rates/{rateId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<RateResponse>> deleteRate(
            Authentication authentication,
            @PathVariable int rateId
    )
    {
        return ResponseEntity.ok(GenericResponse.<RateResponse>builder()
                .message("ok")
                .data(rateService.deleteRate(authentication,  rateId))
                .build());
    }

}
