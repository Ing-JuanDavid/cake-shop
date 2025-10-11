package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.UserMapper;
import com.juan.cakeshop.api.dto.requests.UserDto;
import com.juan.cakeshop.api.dto.responses.UserResponse;
import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.api.repository.UserRepository;
import com.juan.cakeshop.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    final UserRepository userRepository;
    final UserMapper userMapper;

    @Override
    public List<UserResponse> users() {
        return userMapper.toList(userRepository.findAll());
    }

    @Override
    public UserResponse findUser(Long nip) {
        User user = userRepository.findById(nip).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse updateUser(UserDto userDto) {
        return null;
    }

    // Must change userDto from auth class
}
