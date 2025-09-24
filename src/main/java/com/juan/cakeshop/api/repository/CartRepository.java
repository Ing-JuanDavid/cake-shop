package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("SELECT c FROM Cart c JOIN c.user u where u.email = :email")
    Optional<Cart> findByEmail(@Param("email") String email);
}
