package com.kaweel.jpa.specification.dao.repository;

import com.kaweel.jpa.specification.dao.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> , JpaSpecificationExecutor {

    Optional<Customer> findByUserName(String userName);

}
