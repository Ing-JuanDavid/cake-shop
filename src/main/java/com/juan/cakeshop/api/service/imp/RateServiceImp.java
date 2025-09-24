package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.RateMapper;
import com.juan.cakeshop.api.dto.requests.RateDto;
import com.juan.cakeshop.api.dto.responses.RateResponse;
import com.juan.cakeshop.api.model.Product;
import com.juan.cakeshop.api.model.Rate;
import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.api.repository.ProductRepository;
import com.juan.cakeshop.api.repository.RateRepository;
import com.juan.cakeshop.api.repository.UserRepository;
import com.juan.cakeshop.api.service.RateService;
import com.juan.cakeshop.exception.customExceptions.ProductNotFoundException;
import com.juan.cakeshop.exception.customExceptions.RateNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RateServiceImp implements RateService {

    private final RateRepository rateRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final RateMapper rateMapper;

    @Override
    public RateResponse createRate(int productId, RateDto rateDto) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException(productId)
        );

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(
                ()->  new UsernameNotFoundException("User not found")
        );

        Rate rate = rateMapper.toEntity(rateDto, user, product);

        return rateMapper.toResponse(rateRepository.save(rate));
    }

    @Override
    public List<RateResponse> getRatesByProduct(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException(productId)
        );
        return rateMapper.toList(product.getRates());
    }

    @Override
    public RateResponse updateRate(int productId, int rateId, RateDto rateDto) {
        User user = userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );


        Rate savedRate = rateRepository.findByUserAndRateId(user, rateId).orElseThrow(
                ()-> new RateNotFoundException(rateId)
        );

        Product savedProduct = savedRate.getProduct();

        if(! savedProduct.getProductId().equals(productId)) {
            throw  new RateNotFoundException(rateId); //  must change this exception to productMisMatchException
        }

        savedRate = rateMapper.updateFromRateDto(savedRate, rateDto);

        Rate updatedRate = rateRepository.save(savedRate);

        return rateMapper.toResponse(updatedRate);
    }

    @Override
    public RateResponse deleteRate(int rateId) {

        User user = userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        Rate rate = rateRepository.findByUserAndRateId(user, rateId).orElseThrow(
                ()-> new RateNotFoundException(rateId)
        );

        rateRepository.delete(rate);

        return rateMapper.toResponse(rate);
    }

    public RateResponse deleteRateByAdmin(int rateId)
    {
        Rate savedRate = rateRepository.findById(rateId).orElseThrow(
                ()-> new RateNotFoundException(rateId)
        );

        rateRepository.delete(savedRate);

        return rateMapper.toResponse(savedRate);
    }

}
