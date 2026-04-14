package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String userName); // Could happen an error because of attribute's name 'userName' user email instead of

    boolean existsByNip(Long nip);
    boolean existsByEmail(String email);

}
