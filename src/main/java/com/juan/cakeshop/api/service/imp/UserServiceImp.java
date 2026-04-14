package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.requests.UserRegisterDto;
import com.juan.cakeshop.api.dto.requests.UserFilterDto;
import com.juan.cakeshop.api.dto.responses.PaginatedResponse;
import com.juan.cakeshop.api.dto.responses.UserResponse;
import com.juan.cakeshop.api.mapper.UserMapper;
import com.juan.cakeshop.api.dto.requests.UserDto;
import com.juan.cakeshop.api.dto.requests.UserInfoDto;
import com.juan.cakeshop.api.dto.responses.UserSimpleResponse;
import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.api.model.UserDetailsImp;
import com.juan.cakeshop.api.repository.UserRepository;
import com.juan.cakeshop.api.service.UserService;
import com.juan.cakeshop.api.specifications.UserSpecification;
import com.juan.cakeshop.exception.customExceptions.InvalidInputException;
import com.juan.cakeshop.exception.customExceptions.UserAlreadyExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    final UserRepository userRepository;
    final UserMapper userMapper;

    @Override
    public List<UserSimpleResponse> getUsers() {
        return userMapper.toList(userRepository.findAll());
    }

    @Override
    public UserResponse findUser(Long nip) {
        User user = userRepository.findById(nip).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        return userMapper.toCompleteResponse(user);
    }

    @Override
    public UserSimpleResponse createUser(UserRegisterDto userRegisterDto) {

        if(userRepository.existsById(userRegisterDto.getNip())) {
            throw new UserAlreadyExistException("NIP", userRegisterDto.getNip().toString());
        }

        if(userRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new UserAlreadyExistException("email", userRegisterDto.getEmail());
        }

        User user = userRepository.save(userMapper.toEntity(userRegisterDto));

        return userMapper.toSimpleResponse(user);
    }

    @Override
    public UserSimpleResponse updateUser(Long nip, UserDto userDto) {

        User user = userRepository.findById(nip).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        user = userMapper.updateFromDto(user, userDto);

        User updateUser = userRepository.save(user);

        return userMapper.toSimpleResponse(updateUser);
    }

    @Override
    public UserSimpleResponse updateUser(String email, UserInfoDto userInfoDto) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        user = userMapper.updateFromDto(user, userInfoDto);

        User updatedUser = userRepository.save(user);

        return userMapper.toSimpleResponse(updatedUser);
    }

    @Override
    public UserSimpleResponse getUserInfo(UserDetailsImp userDetailsImp) {
        return userMapper.toSimpleResponse(userDetailsImp.getUser());
    }

    @Override
    public UserSimpleResponse lockUser(Long nip) {
        User user = userRepository.findById(nip).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        user.setAccountNonLocked(false);

        return userMapper.toSimpleResponse(userRepository.save(user));
    }

    @Override
    public UserSimpleResponse unLockUser(Long nip) {
        User user = userRepository.findById(nip).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        user.setAccountNonLocked(true);

        return userMapper.toSimpleResponse(userRepository.save(user));
    }

    @Override
    public PaginatedResponse<UserSimpleResponse> getUserss(int currentPage, int sizePage, UserFilterDto filters) {

        if(currentPage<1) throw new InvalidInputException("currentPage");
        if(sizePage<1) throw new InvalidInputException("sizePage");

        Specification<User> spec = Specification.where(UserSpecification.hasName(filters.getName()))
                .and(UserSpecification.hasName(filters.getName()))
                .and(UserSpecification.hasEmail(filters.getEmail()))
                .and(UserSpecification.hasNip(filters.getNip()))
                .and(UserSpecification.hasRole(filters.getRole()))
                .and(UserSpecification.hasAccountNonLocked(filters.getIsAccountNonLocked()));

        Pageable pageable = PageRequest.of(currentPage-1, sizePage);

        Page<User> page = userRepository.findAll(spec, pageable);

        if(page.isEmpty()) return userMapper.toPaginatedResponse(1, page);

        if(currentPage > page.getTotalPages()) throw  new InvalidInputException("currentPage");

        return userMapper.toPaginatedResponse(currentPage, page);
    }

    @Override
    public UserSimpleResponse deleteUserByNip(Long nip) {
        User user = userRepository.findById(nip).orElseThrow(
                ()-> new UsernameNotFoundException("User not found")
        );

        userRepository.delete(user);

        return userMapper.toSimpleResponse(user);
    }
}
