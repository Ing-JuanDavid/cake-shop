package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.model.Cart;
import com.juan.cakeshop.api.model.CartProduct;
import com.juan.cakeshop.api.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {
    Optional<CartProduct> findByCartAndProduct(Cart cart, Product product);

    @Transactional
    long deleteByCart(Cart cart);

}
