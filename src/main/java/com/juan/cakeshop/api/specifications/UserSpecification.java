package com.juan.cakeshop.api.specifications;

import com.juan.cakeshop.api.model.User;
import org.springframework.data.jpa.domain.Specification;

import javax.management.relation.Role;

public class UserSpecification {

    public  static Specification<User> hasNip(Long nip)
    {
        return (root, query, cb) ->
                nip == null ? null : cb.equal(root.get("nip"), nip);
    }

    public  static Specification<User> hasName(String name)
    {
        return (root, query, cb) ->
                name == null ? null : cb.like(cb.lower(root.get("name")), "%"+name.toLowerCase()+"%");
    }

    public static Specification<User> hasEmail(String email)
    {
        return (root, query, cb) ->
                email == null ? null : cb.like(cb.lower(root.get("email")), "%"+email.toLowerCase()+"%");
    }

    public  static Specification<User> hasRole(String role)
    {
        return (root, query, cb) ->
                role == null ? null : cb.like(cb.lower(root.get("rol")), "%"+role.toLowerCase()+"%");
    }

    public  static Specification<User> hasAccountNonLocked(Boolean accoutNonLocked){
        return (root, query, cb) ->
        accoutNonLocked == null ? null : cb.equal(root.get("accountNonLocked"), accoutNonLocked);
    }

}
