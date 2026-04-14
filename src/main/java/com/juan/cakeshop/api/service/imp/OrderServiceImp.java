package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.mapper.OrderMapper;
import com.juan.cakeshop.api.dto.requests.OrderDto;
import com.juan.cakeshop.api.dto.responses.OrderResponse;
import com.juan.cakeshop.api.dto.responses.UpdatedOrderResponse;
import com.juan.cakeshop.api.mapper.ProductMapper;
import com.juan.cakeshop.api.model.*;
import com.juan.cakeshop.api.repository.CartRepository;
import com.juan.cakeshop.api.repository.OrderRepository;
import com.juan.cakeshop.api.repository.UserRepository;
import com.juan.cakeshop.api.service.OrderService;
import com.juan.cakeshop.exception.customExceptions.EmptyCartException;
import com.juan.cakeshop.exception.customExceptions.InvalidInputException;
import com.juan.cakeshop.exception.customExceptions.OrderNotFound;
import com.juan.cakeshop.exception.customExceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final ProductMapper productMapper;

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
    public List<OrderResponse> getAllOrders(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );
        return orderMapper.toList(orderRepository.findAllByUserOrderByDateDesc(user));
    }

    @Override
    public OrderResponse getOrderById(String email, int orderId) {
        userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        Order order = orderRepository.findById(orderId).orElseThrow(
                OrderNotFound::new
        );

        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> getOrdersByProductId(String email, int productId) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        List<Order> orders = user.getOrders().stream()
                .filter(order -> order.getOrderProducts().stream()
                        .anyMatch(orderProduct -> orderProduct.getProduct().getProductId()==productId))
                .toList();



        if(orders.isEmpty()) throw new ProductNotFoundException(productId); //change this line

        return orderMapper.toList(orders);
    }

    @Override
    public PaginatedResponse<OrderResponse> getOrdersByUser(long nip, int pageSize, int currentPage) {

        if(pageSize < 1) throw  new InvalidInputException("pageSize");
        if(currentPage < 1) throw  new InvalidInputException("currentPage");

        User user = userRepository.findById(nip).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize, Sort.by(Sort.Direction.DESC, "date"));

        long totalOrders = orderRepository.countByUserNip(nip);
        int totalPages = (int)Math.ceil((double)totalOrders/pageSize);

        if(totalPages == 0) return orderMapper.toPaginatedResponse(Page.empty(), 1);

        if(currentPage > totalPages) throw new InvalidInputException("currentPage");

        Page<Order> page = orderRepository.findAllByUser(user, pageable);

        return orderMapper.toPaginatedResponse(page, currentPage);
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
