package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.UserDto;
import com.juan.cakeshop.api.dto.responses.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> users();

    UserResponse findUser(Long nip);

    UserResponse updateUser(UserDto userDto);

}
