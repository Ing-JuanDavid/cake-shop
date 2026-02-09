package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.CartDto;
import com.juan.cakeshop.api.dto.responses.CartResponse;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.model.UserDetailsImp;
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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cart")
public class CartController {

    final CartService cartService;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<CartResponse>> addProduct(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp, //if my principal were an object i could use an expression to indicate which attribute to use
            @RequestBody @Valid CartDto cartDto
    )
    {
        return ResponseEntity.ok(GenericResponse.<CartResponse>builder()
                .ok(true)
                .data(cartService.addProduct(userDetailsImp.getUsername(), cartDto))
                .build());
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public  ResponseEntity<GenericResponse<Map<String, Object>>> getCart(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp
    )
    {
        return ResponseEntity.ok(GenericResponse.<Map<String, Object>>builder()
                .ok(true)
                .data(cartService.getCart(userDetailsImp.getUsername()))
                .build());
    }

    // Do delete from cart by product id
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<CartResponse>> deleteProduct(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp,
            @PathVariable  Integer productId
    )
    {
        return ResponseEntity.ok(GenericResponse.<CartResponse>builder()
                .ok(true)
                .data(cartService.deleteById(userDetailsImp.getUsername(), productId))
                .build());
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<Object>> emptyCart(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp
    )
    {
        return ResponseEntity.ok(cartService.emptyCart(userDetailsImp.getUsername()));
    }



}
