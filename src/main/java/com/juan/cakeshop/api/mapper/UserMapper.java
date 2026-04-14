package com.juan.cakeshop.api.mapper;

import com.juan.cakeshop.api.dto.requests.RegisterDto;
import com.juan.cakeshop.api.dto.requests.UserRegisterDto;
import com.juan.cakeshop.api.dto.requests.UserInfoDto;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.UserResponse;
import com.juan.cakeshop.api.dto.responses.UserSimpleResponse;
import com.juan.cakeshop.api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RateMapper rateMapper;
    private final OrderMapper orderMapper;

    public UserSimpleResponse toSimpleResponse(User user)
    {
        return UserSimpleResponse.builder()
                .nip(user.getNip())
                .email(user.getEmail())
                .name(user.getName())
                .roles(List.of("ROLE_"+user.getRol().name()))
                .telf(user.getTelf())
                .address(user.getAddress())
                .sex(user.getSex())
                .birth(user.getBirth())
                .accountNonLocked(user.isAccountNonLocked())
                .build();
    }

    public UserResponse toCompleteResponse(User user)
    {
        return UserResponse.builder()
                .nip(user.getNip())
                .email(user.getEmail())
                .name(user.getName())
                .roles(List.of("ROLE_"+user.getRol().name()))
                .telf(user.getTelf())
                .address(user.getAddress())
                .sex(user.getSex())
                .birth(user.getBirth())
                .accountNonLocked(user.isAccountNonLocked())
                .rates(this.rateMapper.toList(user.getRates()))
                .orders(this.orderMapper.toList(user.getOrders()))
                .build();
    }

    public User toEntity(RegisterDto registerDto)
    {
        return User.builder()
                .nip(registerDto.getNip())
                .email(registerDto.getEmail())
                .name(registerDto.getName())
                .telf(registerDto.getTelf())
                .address(registerDto.getAddress())
                .sex(registerDto.getSex())
                .birth(registerDto.getBirth())
                .build();
    }

    public User toEntity(UserRegisterDto registerDto)
    {
        return User.builder()
                .email(registerDto.getEmail())
                .pass(passwordEncoder.encode(registerDto.getPassword()))
                .nip(registerDto.getNip())
                .name(registerDto.getName())
                .birth(registerDto.getBirth())
                .sex(registerDto.getSex())
                .rol(registerDto.getRol())
                .telf(registerDto.getTelf())
                .address(registerDto.getAddress())
                .accountNonLocked(true)
                .build();
    }

    public List<UserSimpleResponse> toList(List<User> users)
    {
        return users.stream()
                .map(this::toSimpleResponse)
                .toList();
    }

    public User updateFromDto(User user, com.juan.cakeshop.api.dto.requests.UserDto userDto) {
        user.setName(userDto.getName());
        user.setAddress(userDto.getAddress());
        user.setRol(userDto.getRol());
        user.setSex(userDto.getSex());
        user.setTelf(userDto.getTelf());
        user.setBirth(userDto.getBirth());
        return user;
    }

    public User updateFromDto(User user, UserInfoDto userInfoDto) {
        user.setName(userInfoDto.getName());
        user.setAddress(userInfoDto.getAddress());
        user.setSex(userInfoDto.getSex());
        user.setTelf(userInfoDto.getTelf());
        user.setBirth(userInfoDto.getBirth());
        return user;
    }

    public PaginatedResponse<UserSimpleResponse> toPaginatedResponse(int currentPage, Page<User> page)
    {
        return PaginatedResponse.<UserSimpleResponse>builder()
                .currentPage(currentPage)
                .pageLength((int)page.getTotalElements())
                .totalPages(page.getTotalPages())
                .totalElements((int)page.getTotalElements())
                .nextPage(page.hasNext()? ++currentPage : currentPage)
                .data(this.toList(page.getContent()))
                .build();
    }
}
