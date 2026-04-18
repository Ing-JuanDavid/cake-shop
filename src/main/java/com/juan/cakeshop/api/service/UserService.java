package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.UserRegisterDto;
import com.juan.cakeshop.api.dto.requests.UserDto;
import com.juan.cakeshop.api.dto.requests.UserFilterDto;
import com.juan.cakeshop.api.dto.requests.UserInfoDto;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.UserResponse;
import com.juan.cakeshop.api.dto.responses.ProfileInfo;
import com.juan.cakeshop.api.model.UserDetailsImp;

import java.util.List;

public interface UserService {

    List<ProfileInfo> getUsers();

    UserResponse findUser(Long nip);

    ProfileInfo createUser(UserRegisterDto userRegisterDto);

    ProfileInfo updateUser(Long nip, UserDto userDto);

    ProfileInfo updateUser(String  email, UserInfoDto userInfoDto);

    ProfileInfo deleteUserByNip(Long nip);

    ProfileInfo getUserInfo(UserDetailsImp userDetailsImp);

    ProfileInfo lockUser(Long nip);

    ProfileInfo unLockUser(Long nip);

    PaginatedResponse<ProfileInfo> getUserss(int currentPage, int sizePage, UserFilterDto filters);
}
