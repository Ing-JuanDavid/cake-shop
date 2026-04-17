package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.model.UserAddress;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE UserAddress ua SET ua.isDefault = false WHERE ua.user.nip = :nip")
    void clearDefaultByUser(long nip);

    List<UserAddress> findAllByUserNip(long nip);

    Optional<UserAddress> findByUserNipAndIsDefaultTrue(long id);

    Optional<UserAddress> findByAddressIdAndUserNip(int addressId, long nip);



}
