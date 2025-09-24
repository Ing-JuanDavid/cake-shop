package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.CartDto;
import com.juan.cakeshop.api.dto.responses.CartResponse;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    final CartService cartService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<CartResponse>> addProduct(@RequestBody CartDto cartDto)
    {
        return ResponseEntity.ok(GenericResponse.<CartResponse>builder()
                .message("ok")
                .data(cartService.addProduct(cartDto))
                .build());
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public  ResponseEntity<GenericResponse<Map<String, Object>>> getCart()
    {
        return ResponseEntity.ok(GenericResponse.<Map<String, Object>>builder()
                .message("ok")
                .data(cartService.getCart())
                .build());
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<Object>> emptyCart()
    {
        return ResponseEntity.ok(cartService.emptyCart());
    }

}
