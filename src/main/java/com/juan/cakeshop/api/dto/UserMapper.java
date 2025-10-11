package com.juan.cakeshop.api.dto;

import com.juan.cakeshop.api.dto.responses.UserResponse;
import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.auth.dto.UserDto;

import java.util.List;

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

    public User toEntity(UserDto userDto)
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
}
