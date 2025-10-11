package com.juan.cakeshop.api.dto;

import com.juan.cakeshop.api.dto.responses.CartResponse;
import com.juan.cakeshop.api.model.Cart;
import com.juan.cakeshop.api.model.CartProduct;
import com.juan.cakeshop.api.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartProductMapper {
    public CartProduct toEntity(com.juan.cakeshop.api.dto.requests.CartDto cartDto, Product product, Cart cart) {
        return CartProduct.builder()
                .product(product)
                .cart(cart)
                .build();
    }

    public CartResponse toResponse(CartProduct cartProduct) {
        return CartResponse.builder()
                .productName(cartProduct.getProduct().getName())
                .price(cartProduct.getProduct().getPrice())
                .quant(cartProduct.getQuant())
                .build();
    }

    public List<CartResponse> toList(List<CartProduct> cartProducts) {
        return cartProducts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
