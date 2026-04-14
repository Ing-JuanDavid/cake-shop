package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.UserRegisterDto;
import com.juan.cakeshop.api.dto.requests.UserDto;
import com.juan.cakeshop.api.dto.requests.UserFilterDto;
import com.juan.cakeshop.api.dto.requests.UserInfoDto;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.UserResponse;
import com.juan.cakeshop.api.dto.responses.UserSimpleResponse;
import com.juan.cakeshop.api.model.UserDetailsImp;

import java.util.List;

public interface UserService {

    List<UserSimpleResponse> getUsers();

    UserResponse findUser(Long nip);

    UserSimpleResponse createUser(UserRegisterDto userRegisterDto);

    UserSimpleResponse updateUser(Long nip, UserDto userDto);

    UserSimpleResponse updateUser(String  email, UserInfoDto userInfoDto);

    UserSimpleResponse deleteUserByNip(Long nip);

    UserSimpleResponse getUserInfo(UserDetailsImp userDetailsImp);

    UserSimpleResponse lockUser(Long nip);

    UserSimpleResponse unLockUser(Long nip);

    PaginatedResponse<UserSimpleResponse> getUserss(int currentPage, int sizePage, UserFilterDto filters);
}
