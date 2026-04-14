package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.model.Order;
import com.juan.cakeshop.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    long countByUserNip(long nip);
    List<Order> findAllByUserOrderByDateDesc(User user);
    Page<Order> findAllByUser(User user, Pageable pageable);
}
