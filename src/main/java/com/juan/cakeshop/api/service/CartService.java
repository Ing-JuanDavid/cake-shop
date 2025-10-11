package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.CartDto;
import com.juan.cakeshop.api.dto.responses.CartResponse;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.dto.responses.ProductResponse;
import com.juan.cakeshop.api.model.Cart;
import com.juan.cakeshop.api.model.CartProduct;
import com.juan.cakeshop.api.model.OrderProduct;
import com.juan.cakeshop.api.model.Product;

import java.util.List;
import java.util.Map;

public interface CartService {
    CartResponse addProduct(String email, CartDto cartDto);

    Map<String, Object> getCart(String email);

    GenericResponse<Object> emptyCart(String email);

    CartResponse deleteById(String email, Integer productId);

    int calcCartTotal(List<CartProduct> products);
}


