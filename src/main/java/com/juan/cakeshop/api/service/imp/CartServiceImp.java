package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.mapper.CartProductMapper;
import com.juan.cakeshop.api.dto.requests.CartDto;
import com.juan.cakeshop.api.dto.responses.CartResponse;
import com.juan.cakeshop.api.dto.responses.GenericResponse;
import com.juan.cakeshop.api.model.Cart;
import com.juan.cakeshop.api.model.CartProduct;
import com.juan.cakeshop.api.model.Product;
import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.api.repository.CartProductRepository;
import com.juan.cakeshop.api.repository.CartRepository;
import com.juan.cakeshop.api.repository.ProductRepository;
import com.juan.cakeshop.api.repository.UserRepository;
import com.juan.cakeshop.api.service.CartService;
import com.juan.cakeshop.exception.customExceptions.*;
import lombok.RequiredArgsConstructor;
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
    final UserRepository userRepository;
    final CartProductMapper cartProductMapper;

    @Override
    public CartResponse addProduct(String email, CartDto cartDto) {
        CartProduct cartProduct;

        Cart cart = cartRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        Product product = productRepository.findById(cartDto.getProductId()).orElseThrow(
                ()-> new ProductNotFoundException(cartDto.getProductId())
        );

        cartProduct = cartProductRepository.findByCartAndProduct(cart, product).orElse(null);

        int newQuant = cartDto.getQuant();

       if(cartProduct == null){
           cartProduct = cartProductMapper.toEntity(cartDto, product, cart);
       } else {
           int oldQuant = cartProduct.getQuant();

           if(oldQuant > newQuant) newQuant = oldQuant - (oldQuant - newQuant);

           if(oldQuant < newQuant) newQuant = oldQuant + (newQuant - oldQuant);
       }

       if(newQuant == 0) throw new InvalidQuantProductException(newQuant);

       if(newQuant > product.getQuant()) throw new ProductOutOfStockException(product.getProductId());

       cartProduct.setQuant(newQuant);

        return cartProductMapper.toResponse(cartProductRepository.save(cartProduct));
    }

    @Override
    public Map<String, Object> getCart(String email) {

        Cart cart = cartRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        cart.getCartProduct().removeIf(cartProduct -> cartProduct.getQuant()==0);

        cartRepository.save(cart);

        List<CartProduct> cartProducts = cart.getCartProduct();

        return Map.of(
                "products",cartProductMapper.toList(cartProducts),
                "cartTotal",this.calcCartTotal(cartProducts)
        );
    }

    public int calcCartTotal(List<CartProduct> cartProducts)
    {
        int totalCart = 0;
        for(CartProduct cartProduct: cartProducts) {
            totalCart += cartProduct.getProduct().getPrice()*cartProduct.getQuant();
        }
        return totalCart;
    }

    @Override
    public GenericResponse<Object> emptyCart(String email) {

        Cart cart = cartRepository.findByEmail(email).orElseThrow(
                ()-> new CartNotFoudException()
        );

        List<CartProduct> cartProducts = cart.getCartProduct();

        if(cartProducts.isEmpty()) throw new EmptyCartException("Your cart already is empty");

        cartProductRepository.deleteByCart(cart);

        return GenericResponse.builder()
                .message("ok")
                .data(null)
                .build();
    }

    @Override
    public CartResponse deleteById(String email, Integer productId) {

        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException(productId)
        );

        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        Cart cart = user.getCart();

        if(cart.getCartProduct().isEmpty()) throw new EmptyCartException("Your cart already is empty");

        CartProduct productInCart = cart.getCartProduct().stream()
                        .filter(cp -> cp.getProduct().equals(product))
                        .findFirst()
                        .orElseThrow(()->new ProductNotFoundException(productId));

        cart.getCartProduct().remove(productInCart);

        cartRepository.save(cart);

        return cartProductMapper.toResponse(productInCart);
    }


}
