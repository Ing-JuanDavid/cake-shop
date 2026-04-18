package com.juan.cakeshop.api.mapper;

import com.juan.cakeshop.api.dto.OrderStatus;
import com.juan.cakeshop.api.dto.responses.OrderResponse;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.UpdatedOrderResponse;
import com.juan.cakeshop.api.model.CartProduct;
import com.juan.cakeshop.api.model.Order;
import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.api.model.UserAddress;
import com.juan.cakeshop.api.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMapper {

    private final CartService cartService;
    private final UserAddressMapper userAddressMapper;
    private final OrderProductMapper orderProductMapper;

    public Order toEntity(User user, List<CartProduct> cartProducts, UserAddress defaulAddress)
    {
         Order order = Order.builder()
                .user(user)
                .total(cartService.calcCartTotal(cartProducts))
                .date(LocalDate.now())
                .status(OrderStatus.PENDING)
                .address(defaulAddress)
                .build();

         order.setOrderProducts(orderProductMapper.getOrderProductsFromCartProductList(cartProducts, order));

         return order;
    }


    // having in account this method for debugging
    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .OrderId(order.getOrderId())
                .products(orderProductMapper.toList(order.getOrderProducts()))
                .total(order.getTotal())
                .date(order.getDate())
                .status(order.getStatus().name())
                .address(userAddressMapper.toResponse(order.getAddress()))
                .build();
    }

    public UpdatedOrderResponse toUpdateResponse(Order order) {
        return UpdatedOrderResponse.builder()
                .orderId(order.getOrderId())
                .status(order.getStatus().name())
                .updateDate(LocalDate.now())
                .build();
    }

    public List<OrderResponse> toList(List<Order> orders) {
        return orders.stream()
                .map(this::toResponse)
                .toList();
    }

    public PaginatedResponse<OrderResponse> toPaginatedResponse(Page<Order> page, int currentPage)
    {
        return PaginatedResponse.<OrderResponse>builder()
                .pageLength(page.getNumberOfElements())
                .totalPages(page.getTotalPages())
                .currentPage(currentPage)
                .nextPage(page.hasNext() ? ++currentPage : currentPage)
                .totalElements((int) page.getTotalElements())
                .data(this.toList(page.getContent()))
                .build();
    }
}
