package com.juan.cakeshop.api.controller;

import com.juan.cakeshop.api.dto.requests.OrderDto;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.dto.responses.OrderResponse;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.UpdatedOrderResponse;
import com.juan.cakeshop.api.model.UserDetailsImp;
import com.juan.cakeshop.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<OrderResponse>> createOrder(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp
    )
    {
        return ResponseEntity.ok(GenericResponse.<OrderResponse>builder()
                .ok(true)
                .data(orderService.CreateOrder(userDetailsImp.getUsername()))
                .build());
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GenericResponse<List<OrderResponse>>> getAllOrders(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp
    )
    {
        return ResponseEntity.ok(GenericResponse.<List<OrderResponse>>builder()
                .ok(true)
                .data(orderService.getAllOrders(userDetailsImp.getUsername()))
                .build());
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{orderId}")
    public ResponseEntity<GenericResponse<OrderResponse>> getOrderById(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp,
            @PathVariable int orderId)
    {
        return ResponseEntity.ok(GenericResponse.<OrderResponse>builder()
                .ok(true)
                .data(orderService.getOrderById(userDetailsImp.getUsername(), orderId))
                .build()) ;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/users/{nip}")
    public ResponseEntity<GenericResponse<PaginatedResponse<OrderResponse>>> getOrdersByUser(
            @PathVariable long nip,
            @RequestParam(defaultValue = "5", required = false) int pageSize,
            @RequestParam(defaultValue = "1", required = false) int currentPage
    )
    {
     return ResponseEntity.ok(
             GenericResponse.<PaginatedResponse<OrderResponse>>builder()
                     .ok(true)
                     .data(orderService.getOrdersByUser(nip, pageSize, currentPage))
                     .build()
     );
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/product/{productId}")
    public ResponseEntity<GenericResponse<List<OrderResponse>>> getOrdersByProductId(
            @AuthenticationPrincipal UserDetailsImp userDetailsImp,
            @PathVariable int productId
    )
    {
        return ResponseEntity.ok(GenericResponse.<List<OrderResponse>>builder()
                .ok(true)
                .data(orderService.getOrdersByProductId(userDetailsImp.getUsername(), productId))
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
                .ok(true)
                .data(orderService.updateStatus(orderId, orderDto))
                .build());
    }

}
