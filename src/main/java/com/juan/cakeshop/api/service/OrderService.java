package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.OrderStatus;
import com.juan.cakeshop.api.dto.requests.OrderDto;
import com.juan.cakeshop.api.dto.responses.OrderResponse;
import com.juan.cakeshop.api.dto.responses.UpdatedOrderResponse;

import java.util.List;

public interface OrderService {

    public OrderResponse CreateOrder(String email);

    public List<OrderResponse>  getALlOrders(String email);

    UpdatedOrderResponse updateStatus(int orderId, OrderDto orderDto);
}
