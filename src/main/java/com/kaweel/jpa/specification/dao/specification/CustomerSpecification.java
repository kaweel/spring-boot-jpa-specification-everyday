package com.kaweel.jpa.specification.dao.specification;

import com.kaweel.jpa.specification.dao.domain.Address;
import com.kaweel.jpa.specification.dao.domain.Customer;
import com.kaweel.jpa.specification.enums.AddressType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification {

    public static Specification<Customer> searchByCriteria(CustomerSearchCriteria criteria) {
        return (Specification<Customer>) (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            String userName = criteria.getUserName();
            if (!StringUtils.isEmpty(userName)){
                predicates.add(builder.equal(root.get("userName"), userName));
            }

            Integer age = criteria.getAge();
            if (null != age){
                predicates.add(builder.equal(root.get("age"), age));
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    public static Specification<Customer> userNameIs(String userName) {
        return (Specification<Customer>) (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> builder.equal(root.get("userName"), userName);
    }

    public static Specification<Customer> hasAgeMoreThan(Integer age) {
        return (Specification<Customer>) (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> builder.greaterThan(root.get("age"), age);
    }

    public static Specification<Customer> hasOffice() {
        return (Specification<Customer>) (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Customer, Address> address = root.join("address", JoinType.LEFT);
            return builder.equal(address.get("type"), AddressType.OFFICE);
        };
    }

    public static Specification<Customer> hasHome() {
        return (Specification<Customer>) (Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<Customer, Address> address = root.join("address", JoinType.LEFT);
            return builder.equal(address.get("type"), AddressType.HOME);
        };
    }

}
