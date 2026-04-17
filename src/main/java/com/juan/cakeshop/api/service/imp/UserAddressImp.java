package com.juan.cakeshop.api.service.imp;

import com.juan.cakeshop.api.dto.requests.UserAddressDto;
import com.juan.cakeshop.api.dto.responses.UserAddressResponse;
import com.juan.cakeshop.api.mapper.UserAddressMapper;
import com.juan.cakeshop.api.model.User;
import com.juan.cakeshop.api.model.UserAddress;
import com.juan.cakeshop.api.repository.UserAddressRepository;
import com.juan.cakeshop.api.repository.UserRepository;
import com.juan.cakeshop.api.service.UserAddressService;
import com.juan.cakeshop.exception.customExceptions.UserAddressException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAddressImp implements UserAddressService {

    final UserAddressRepository userAddressRepository;
    final UserRepository userRepository;
    final UserAddressMapper userAddressMapper;



    public UserAddressResponse createAddress(User user, UserAddressDto addressDto) {

        UserAddress address = userAddressMapper.toEntity(addressDto, user);

        if (address.isDefault()) {
            userAddressRepository.clearDefaultByUser(address.getUser().getNip());
        }

        return userAddressMapper.toResponse(userAddressRepository.save(address));
    }

    @Override
    public UserAddressResponse getAddress(long nip, int addressId) {
        UserAddress address = userAddressRepository.findByAddressIdAndUserNip(addressId, nip).orElseThrow(
                () -> new UserAddressException("Address not found", HttpStatus.NOT_FOUND)
        );

        return userAddressMapper.toResponse(address);
    }

    /**
     * Get all of user's registered address*/
    @Override
    public List<UserAddressResponse> getAddresses(long nip) {
        return userAddressMapper.toList(userAddressRepository.findAllByUserNip(nip));
    }


    @Override
    public UserAddressResponse getDefaultAddress(long nip) {

        return userAddressMapper.toResponse(
                userAddressRepository.findByUserNipAndIsDefaultTrue(nip).orElseThrow(
                        ()-> new UserAddressException("Direccion por defecto no encontrada", HttpStatus.NOT_FOUND)
                )
        );
    }

    @Override
    public UserAddressResponse setDefaultAddress(long nip, int addressId) {
        UserAddress savedAddress = userAddressRepository.findByAddressIdAndUserNip(addressId, nip).orElseThrow(
                ()-> new UserAddressException("Direccion no encontrada", HttpStatus.NOT_FOUND)
        );

        if(savedAddress.isDefault()) return userAddressMapper.toResponse(savedAddress);

        userAddressRepository.clearDefaultByUser(nip);
        savedAddress.setDefault(true);
        return userAddressMapper.toResponse(userAddressRepository.save(savedAddress));
    }

    @Override
    public UserAddressResponse updateAddress(long nip, int addressId, UserAddressDto addressDto) {
        UserAddress savedAddress = userAddressRepository.findByAddressIdAndUserNip(addressId, nip).orElseThrow(
                ()-> new UserAddressException("Direccion no encontrada", HttpStatus.NOT_FOUND)
        );

        UserAddress updatedAddress = userAddressMapper.updateFromDto(savedAddress, addressDto);

        if(updatedAddress.isDefault()) userAddressRepository.clearDefaultByUser(nip);

        System.out.println("Defaukt value: "+updatedAddress.isDefault());

        return userAddressMapper.toResponse(userAddressRepository.save(updatedAddress));
    }


    @Override
    public void deleteAddress(long nip, int addressId) {
        UserAddress address = userAddressRepository.findByAddressIdAndUserNip(addressId, nip).orElseThrow(
                ()-> new UserAddressException("Direccion no encontrada", HttpStatus.NOT_FOUND)
        );

        userAddressRepository.delete(address);
    }
}
