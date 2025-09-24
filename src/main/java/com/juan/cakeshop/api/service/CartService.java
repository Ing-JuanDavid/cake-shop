package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.responses.CartResponse;
import com.juan.cakeshop.api.dto.responses.GenericResponse;

import java.util.List;
import java.util.Map;

public interface CartService {
    CartResponse addProduct(com.juan.cakeshop.api.dto.requests.CartDto cartDtort);

    Map<String, Object> getCart();

    GenericResponse<Object> emptyCart();

}


