package com.juan.cakeshop.api.mapper;

import com.juan.cakeshop.api.dto.responses.CartResponse;
import com.juan.cakeshop.api.model.CartProduct;
import com.juan.cakeshop.api.model.Order;
import com.juan.cakeshop.api.model.Product;
import org.springframework.stereotype.Service;
import com.juan.cakeshop.api.model.OrderProduct;

import java.util.List;

@Service
 class OrderProductMapper {

    public List<OrderProduct> getOrderProductsFromCartProductList(List<CartProduct> cartProducts, Order order)
    {
        return cartProducts.stream()
                .map(cartProduct -> {
                    Product product = cartProduct.getProduct();


                    return OrderProduct.builder()
                            .order(order)
                            .productId(product.getProductId())
                            .productName(product.getName())
                            .price(product.getPrice())
                            .quant(cartProduct.getQuant())
                            .imageUrl(
                                    product.getProductImages() != null && !product.getProductImages().isEmpty()
                                            ? product.getProductImages().get(0).getImageUrl() : null
                            )
                        .build();
                }
                ).toList();

    }

    public List<CartResponse> toList(List<OrderProduct> orderProducts)
    {
        return orderProducts.stream()
                .map(orderProduct -> CartResponse.builder()
                        .productId(orderProduct.getProductId())
                        .name(orderProduct.getProductName())
                        .price(orderProduct.getPrice() == null ? 0 : orderProduct.getPrice())
                        .quant(orderProduct.getQuant() == null ? 0 : orderProduct.getQuant())
                        .img(orderProduct.getImageUrl())
                        .build())
                .toList();
    }

}
