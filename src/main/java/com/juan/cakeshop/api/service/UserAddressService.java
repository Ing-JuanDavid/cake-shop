package com.juan.cakeshop.api.service;

import com.juan.cakeshop.api.dto.requests.UserAddressDto;
import com.juan.cakeshop.api.dto.responses.UserAddressResponse;
import com.juan.cakeshop.api.model.User;

import java.util.List;

public interface UserAddressService {

    /**
     * Create a new address
     * This method is only for Users
     */
    UserAddressResponse createAddress(User user, UserAddressDto addressDto);

    UserAddressResponse getAddress(long nip, int addressId);

    List<UserAddressResponse> getAddresses(long  nip);

    /**
     * Find default address by using user's nip
     * This method is only for Users
     * */
    UserAddressResponse getDefaultAddress(long nip);

    /**
     * Set default address by using user's nip and addressId
     * This method is only for Users
     * */
    UserAddressResponse setDefaultAddress(long nip, int addressId);

    /**
     * Update address by using user's nip, addressId and addressDto
     * This method is only for Users
     * */
    UserAddressResponse updateAddress(long nip, int addressId, UserAddressDto addressDto);

    /**
     * Delete an address by passing an addressId
     * This method is for both users and admins
     * */
    void deleteAddress(long nip, int addressId);


}
