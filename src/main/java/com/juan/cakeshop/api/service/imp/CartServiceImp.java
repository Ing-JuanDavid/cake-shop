package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.CartProductMapper;
import com.juan.cakeshop.api.dto.responses.CartResponse;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.model.Cart;
import com.juan.cakeshop.api.model.CartProduct;
import com.juan.cakeshop.api.model.Product;
import com.juan.cakeshop.api.repository.CartProductRepository;
import com.juan.cakeshop.api.repository.CartRepository;
import com.juan.cakeshop.api.repository.ProductRepository;
import com.juan.cakeshop.api.service.CartService;
import com.juan.cakeshop.exception.customExceptions.EmptyCartException;
import com.juan.cakeshop.exception.customExceptions.InvalidQuantProductException;
import com.juan.cakeshop.exception.customExceptions.ProductNotFoundException;
import com.juan.cakeshop.exception.customExceptions.ProductOutOfStockException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartServiceImp implements CartService {

    final CartRepository cartRepository;
    final ProductRepository productRepository;
    final CartProductRepository cartProductRepository;
    final CartProductMapper cartProductMapper;

    @Override
    public CartResponse addProduct(com.juan.cakeshop.api.dto.requests.CartDto cartDto) {
        CartProduct cartProduct;

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Cart cart = cartRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        Product product = productRepository.findById(cartDto.getProductId()).orElseThrow(
                ()-> new ProductNotFoundException(cartDto.getProductId())
        );

        cartProduct = cartProductRepository.findByCartAndProduct(cart, product).orElse(null);

        int newQuant = (cartProduct == null)
                ? cartDto.getQuant()
                : cartProduct.getQuant() + cartDto.getQuant();

        if(newQuant <= 0) throw  new InvalidQuantProductException(newQuant);

       if(newQuant > product.getQuant() ) throw new ProductOutOfStockException(cartDto.getProductId());

       if (cartProduct == null) {
            cartProduct = cartProductMapper.toEntity(cartDto, product, cart);
       }
       else {
            cartProduct.setQuant(newQuant);
       }
       return cartProductMapper.toResponse(cartProductRepository.save(cartProduct));
    }

    @Override
    public Map<String, Object> getCart() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Cart cart = cartRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        List<CartProduct> cartProducts = cartProductRepository.findByCart(cart);

        return Map.of(
                "products",cartProductMapper.toList(cartProducts),
                "cartTotal",this.calcCartTotal(cartProducts)
        );
    }

    private int calcCartTotal(List<CartProduct> cartProducts)
    {
        int totalCart = 0;
        for(CartProduct cartProduct: cartProducts) {
            totalCart += cartProduct.getProduct().getPrice()*cartProduct.getQuant();
        }
        return totalCart;
    }

    @Override
    public GenericResponse<Object> emptyCart() {
        String email =SecurityContextHolder.getContext().getAuthentication().getName();

        Cart cart = cartRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        boolean isEmpty = (cartProductRepository.deleteByCart(cart) == 0)
        ? false
        : true;

        if(! isEmpty) {
            throw new EmptyCartException();
        }

        return GenericResponse.builder()
                .message("ok")
                .data(null)
                .build();
    }
}
