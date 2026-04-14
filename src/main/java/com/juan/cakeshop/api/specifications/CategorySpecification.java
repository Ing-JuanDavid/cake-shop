package com.juan.cakeshop.api.specifications;

import com.juan.cakeshop.api.model.Category;
import com.juan.cakeshop.api.model.Product;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {
    public static Specification<Category> hasName(String name )
    {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%"+name.toLowerCase()+"%");
    }

    public static Specification<Category> hasMaxProducts(Integer maxProducts ) {
        return (root, query, cb) -> {
            if (maxProducts == null) return null;

            // count the products related to this category
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Product> productRoot = subquery.from(Product.class);

            subquery.select(cb.count(productRoot))
                    .where(cb.equal(productRoot.get("category"), root));

            return cb.lessThanOrEqualTo(subquery, (long) maxProducts);
        };
    }

    public static Specification<Category> hasMinProducts(Integer minProducts ) {
        return (root, query, cb) -> {
            if (minProducts == null) return null;

            // count the products related to this category
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Product> productRoot = subquery.from(Product.class);

            subquery.select(cb.count(productRoot))
                    .where(cb.equal(productRoot.get("category"), root));

            return cb.greaterThanOrEqualTo(subquery, (long) minProducts);
        };
    }
}
