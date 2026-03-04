package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.UserDto;
import com.juan.cakeshop.api.dto.requests.UserInfoDto;
import com.juan.cakeshop.api.dto.responses.UserResponse;
import com.juan.cakeshop.api.model.UserDetailsImp;

import java.util.List;

public interface UserService {

    List<UserResponse> getUsers();

    UserResponse findUser(Long nip);

    UserResponse updateUser(Long nip, UserDto userDto);

    UserResponse updateUser(String  email, UserInfoDto userInfoDto);

    UserResponse deleteUserByNip(Long nip);

    UserResponse getUserInfo(UserDetailsImp userDetailsImp);

    UserResponse lockUser(Long nip);

    UserResponse unLockUser(Long nip);
}
