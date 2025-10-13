package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.UserDto;
import com.juan.cakeshop.api.dto.requests.UserInfoDto;
import com.juan.cakeshop.api.dto.responses.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getUsers();

    UserResponse findUser(Long nip);

    UserResponse updateUser(Long nip, UserDto userDto);

    UserResponse updateUser(String  email, UserInfoDto userInfoDto);

    UserResponse deleteUserByNip(Long nip);

}
