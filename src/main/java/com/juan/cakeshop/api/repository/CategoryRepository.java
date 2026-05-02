package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {


    boolean existsByName(String name);
}
