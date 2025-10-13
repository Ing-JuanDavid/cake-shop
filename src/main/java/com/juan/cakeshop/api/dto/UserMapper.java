package com.juan.cakeshop.api.dto;

import com.juan.cakeshop.api.dto.requests.UserInfoDto;
import com.juan.cakeshop.api.dto.responses.UserResponse;
import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.auth.dto.AuthDto;
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

    public User toEntity(AuthDto userDto)
    {
        return User.builder()
                .nip(userDto.getNip())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .telf(userDto.getTelf())
                .address(userDto.getAddress())
                .sex(userDto.getSex())
                .birth(userDto.getBirth())
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
