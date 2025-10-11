package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
