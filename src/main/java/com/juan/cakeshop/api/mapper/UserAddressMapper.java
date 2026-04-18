package com.juan.cakeshop.api.mapper;

import com.juan.cakeshop.api.dto.requests.UserAddressDto;
import com.juan.cakeshop.api.dto.responses.UserAddressResponse;
import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.api.model.UserAddress;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressMapper {
    public UserAddress toEntity(UserAddressDto addressDto, User user)
    {
        return UserAddress.builder()
                .city(addressDto.getCity())
                .department(addressDto.getDepartment())
                .addressLine(addressDto.getAddressLine())
                .description(addressDto.getDescription())
                .isDefault(addressDto.isDefault())
                .user(user).build();
    }

    public UserAddressResponse toResponse(UserAddress address)
    {
        return UserAddressResponse.builder()
                .addressId(address.getAddressId())
                .city(address.getCity())
                .department(address.getDepartment())
                .addressLine(address.getAddressLine())
                .description(address.getDescription())
                .nip(address.getUser().getNip()).build();
    }

    public List<UserAddressResponse> toList(List<UserAddress> addresses)
    {
        return addresses.stream().map(this::toResponse).toList();
    }

    public UserAddress updateFromDto(UserAddress savedAddress, UserAddressDto addressDto)
    {
        savedAddress.setCity(addressDto.getCity());
        savedAddress.setDepartment(addressDto.getDepartment());
        savedAddress.setAddressLine(addressDto.getAddressLine());
        savedAddress.setDescription(addressDto.getDescription());
        savedAddress.setDefault(addressDto.isDefault());
        return savedAddress;
    }

}
