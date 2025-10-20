package com.juan.cakeshop.api.mapper;

import com.juan.cakeshop.api.dto.requests.RegisterDto;
import com.juan.cakeshop.api.dto.requests.UserInfoDto;
import com.juan.cakeshop.api.dto.responses.UserResponse;
import com.juan.cakeshop.api.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserMapper {
    public UserResponse toResponse(User user)
    {
        return UserResponse.builder()
                .nip(user.getNip())
                .email(user.getEmail())
                .name(user.getName())
                .rol(user.getRol())
                .telf(user.getTelf())
                .address(user.getAddress())
                .sex(user.getSex())
                .birth(user.getBirth())
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

    public List<UserResponse> toList(List<User> users)
    {
        return users.stream()
                .map(this::toResponse)
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
}
