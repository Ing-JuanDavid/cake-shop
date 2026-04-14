package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.OrderDto;
import com.juan.cakeshop.api.dto.responses.OrderResponse;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.UpdatedOrderResponse;

import java.util.List;

public interface OrderService {

    public OrderResponse CreateOrder(String email);

    public List<OrderResponse> getAllOrders(String email);

    public OrderResponse getOrderById(String email, int orderId);

    public List<OrderResponse> getOrdersByProductId(String email, int productId);

    public PaginatedResponse<OrderResponse> getOrdersByUser(long nip, int pageSize, int currentPage);
    UpdatedOrderResponse updateStatus(int orderId, OrderDto orderDto);


}
