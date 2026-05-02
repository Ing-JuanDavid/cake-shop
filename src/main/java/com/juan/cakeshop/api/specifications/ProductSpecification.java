package com.juan.cakeshop.api.specifications;

import com.juan.cakeshop.api.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> hasName (String name)
    {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%" +name.toLowerCase()+ "%");
    }

    public static Specification<Product> hasCategory (String category)
    {
        return (root, query, cb) ->
                category == null ? null : cb.equal(
                        cb.lower(root.join("category").get("name")),
                        category.toLowerCase()
                );
    }

    public static Specification<Product> hasMinPrice(Integer minPrice)
    {
        return (root, query, cb) ->
                minPrice == null ? null :
                        cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Product> hasMaxPrice(Integer maxPrice)
    {
        return (root, query, cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Product> hasAvailable(Boolean isAvailable )
    {
        return (root, query, cb) ->
                isAvailable == null ? null : isAvailable  ? cb.greaterThan(root.get("quant"), 0) :  cb.equal(root.get("quant"), 0);
    }

    public static  Specification<Product> hasActive(Boolean isActive)
    {
        return (root, query, cb) ->

            isActive == null ? null : isActive ? cb.equal(root.get("isActive"), true) : cb.equal(root.get("isActive"),false);
    }


}
