package com.juan.cakeshop.api.repository;

import com.juan.cakeshop.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
