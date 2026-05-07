package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.ProductImageDto;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.dto.responses.ProductImageResponse;
import com.juan.cakeshop.api.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("images")
@CrossOrigin(origins = "http://localhost:4200")
public class PorductImageController {

    private final ProductImageService productImageService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productImageId}")
    public ResponseEntity<GenericResponse<ProductImageResponse>> updateProductImage(
            @PathVariable("productImageId") int productImageId,
            @RequestParam (defaultValue = "false") boolean isMain
    )
    {
       return ResponseEntity.ok(
               GenericResponse.<ProductImageResponse>builder()
                       .ok(true)
                       .data(productImageService.updateProductImage(isMain, productImageId))
                       .build()
       );
    }
}
