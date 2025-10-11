package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.CartDto;
import com.juan.cakeshop.api.dto.responses.CartResponse;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    final CartService cartService;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<CartResponse>> addProduct(
            @AuthenticationPrincipal String email, //if my principal were an object i could use an expression to indicate which attribute to use
            @RequestBody @Valid CartDto cartDto
    )
    {
        return ResponseEntity.ok(GenericResponse.<CartResponse>builder()
                .message("ok")
                .data(cartService.addProduct(email, cartDto))
                .build());
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public  ResponseEntity<GenericResponse<Map<String, Object>>> getCart(
            @AuthenticationPrincipal String email
    )
    {
        return ResponseEntity.ok(GenericResponse.<Map<String, Object>>builder()
                .message("ok")
                .data(cartService.getCart(email))
                .build());
    }

    // Do delete from cart by product id
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<CartResponse>> deleteProduct(
            @AuthenticationPrincipal String email,
            @PathVariable  Integer productId
    )
    {
        return ResponseEntity.ok(GenericResponse.<CartResponse>builder()
                .message("ok")
                .data(cartService.deleteById(email, productId))
                .build());
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<Object>> emptyCart(
            @AuthenticationPrincipal String email
    )
    {
        return ResponseEntity.ok(cartService.emptyCart(email));
    }



}
