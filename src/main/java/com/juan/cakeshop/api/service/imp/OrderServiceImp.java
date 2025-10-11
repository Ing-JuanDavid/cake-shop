package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.OrderMapper;
import com.juan.cakeshop.api.dto.requests.OrderDto;
import com.juan.cakeshop.api.dto.responses.OrderResponse;
import com.juan.cakeshop.api.dto.responses.UpdatedOrderResponse;
import com.juan.cakeshop.api.model.*;
import com.juan.cakeshop.api.repository.CartRepository;
import com.juan.cakeshop.api.repository.OrderRepository;
import com.juan.cakeshop.api.repository.UserRepository;
import com.juan.cakeshop.api.service.OrderService;
import com.juan.cakeshop.exception.customExceptions.EmptyCartException;
import com.juan.cakeshop.exception.customExceptions.OrderNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CartRepository cartRepository;

    @Override
    public OrderResponse CreateOrder(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        Cart cart = user.getCart();

        List<CartProduct> cartProducts = cart.getCartProduct();

        if(cartProducts.isEmpty()) throw new EmptyCartException("Cart empty");

        Order order = orderMapper.toEntity(user, cartProducts);

        cart.getCartProduct().clear();
        cartRepository.save(cart);

        return orderMapper.toResponse(orderRepository.save(order));    }

    @Override
    public List<OrderResponse> getALlOrders(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        return orderMapper.toList(user.getOrders());
    }

    @Override
    public UpdatedOrderResponse updateStatus(int orderId, OrderDto orderDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                OrderNotFound::new
        );

        order.setStatus(orderDto.getOrderStatus());

        return orderMapper.toUpdateResponse(orderRepository.save(order));
    }
}
