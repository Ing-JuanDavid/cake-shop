package com.juan.cakeshop.api.mapper;

import com.juan.cakeshop.api.dto.responses.CartResponse;
import com.juan.cakeshop.api.model.CartProduct;
import com.juan.cakeshop.api.model.Order;
import org.springframework.stereotype.Service;
import com.juan.cakeshop.api.model.OrderProduct;

import java.util.List;

@Service
 class OrderProductMapper {

    public List<OrderProduct> getOrderProductsFromCartProductList(List<CartProduct> cartProducts, Order order)
    {
        return cartProducts.stream()
                .map(cartProduct -> OrderProduct.builder()
                        .product(cartProduct.getProduct())
                        .order(order)
                        .quant(cartProduct.getQuant())
                        .build())
                .toList();
    }

    public List<CartResponse> toList(List<OrderProduct> orderProducts)
    {
        return orderProducts.stream()
                .map(orderProduct -> CartResponse.builder()
                        .productName(orderProduct.getProduct().getName())
                        .price(orderProduct.getProduct().getPrice())
                        .quant(orderProduct.getQuant())
                        .build())
                .toList();
    }

}
