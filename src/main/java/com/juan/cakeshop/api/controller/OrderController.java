package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.OrderDto;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.dto.responses.OrderResponse;
import com.juan.cakeshop.api.dto.responses.UpdatedOrderResponse;
import com.juan.cakeshop.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<OrderResponse>> createOrder(
            @AuthenticationPrincipal String email
    )
    {
        return ResponseEntity.ok(GenericResponse.<OrderResponse>builder()
                .message("ok")
                .data(orderService.CreateOrder(email))
                .build());
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<List<OrderResponse>>> getAllOrders(
            @AuthenticationPrincipal String email
    )
    {
        return ResponseEntity.ok(GenericResponse.<List<OrderResponse>>builder()
                .message("ok")
                .data(orderService.getALlOrders(email))
                .build());
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponse<UpdatedOrderResponse>> updateOrder (
            @PathVariable int orderId,
            @RequestBody OrderDto orderDto
    )
    {
        return ResponseEntity.ok(GenericResponse.<UpdatedOrderResponse>builder()
                .message("ok")
                .data(orderService.updateStatus(orderId, orderDto))
                .build());
    }

}
