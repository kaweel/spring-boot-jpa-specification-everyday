package com.kaweel.jpa.specification.dao.repository;

import com.kaweel.jpa.specification.dao.domain.Customer;
import com.kaweel.jpa.specification.dao.specification.CustomerSearchCriteria;
import com.kaweel.jpa.specification.dao.specification.CustomerSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Sql(value = {"/sql_script/customer_initial.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql_script/customer_cleanup.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class CustomerRepository_Test {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("find customer by username is kaweel should not Found")
    public void find_customer_by_username_not_found() {
        List<Customer> actual = customerRepository.findAll(Specification.where(CustomerSpecification.userNameIs("kaweel")));
        assertEquals(0, actual.size());
    }

    @Test
    @DisplayName("find customer by username is milli should found")
    public void find_customer_by_username_found() {
        List<Customer> actual = customerRepository.findAll(Specification.where(CustomerSpecification.userNameIs("milli")));
        assertEquals(1, actual.size());
    }

    @Test
    @DisplayName("find customer has age more than 18 should found two customers")
    public void find_customer_has_age_more_than_eighteen() {
        List<Customer> actual = customerRepository.findAll(Specification.where(CustomerSpecification.hasAgeMoreThan(18)));
        assertEquals(2, actual.size());
    }

    @Test
    @DisplayName("find customer has age more than 15 should found three customers")
    public void find_customer_has_age_more_than_fifteen() {
        List<Customer> actual = customerRepository.findAll(Specification.where(CustomerSpecification.hasAgeMoreThan(15)));
        assertEquals(3, actual.size());
    }

    @Test
    @DisplayName("find customer has office should found two customers")
    public void find_customer_has_office() {
        List<Customer> actual = customerRepository.findAll(Specification.where(CustomerSpecification.hasOffice()));
        assertEquals(3, actual.size());
    }

    @Test
    @DisplayName("find customer has home should found three customers")
    public void find_customer_has_home() {
        List<Customer> actual = customerRepository.findAll(Specification.where(CustomerSpecification.hasHome()));
        assertEquals(3, actual.size());
    }

    @Test
    @DisplayName("find customer has age more than 18 and has office should found two customers")
    public void find_customer_has_age_more_than_and_home() {
        List<Customer> actual = customerRepository.findAll(Specification.where(CustomerSpecification.hasAgeMoreThan(18)).and(CustomerSpecification.hasOffice()));
        assertEquals(1, actual.size());
    }

    @Test
    @DisplayName("find customer by criteria and criteria is null should get null pointer exception")
    public void find_customer_by_criteria_and_criteria_is_null() {
        Assertions.assertThrows(NullPointerException.class, () -> customerRepository.findAll(CustomerSpecification.searchByCriteria(null)));
    }

    @Test
    @DisplayName("find customer by criteria and criteria value is null should found all customers")
    public void find_customer_by_criteria_and_criteria_value_is_null() {
        List<Customer> actual = customerRepository.findAll(CustomerSpecification.searchByCriteria(new CustomerSearchCriteria()));
        assertEquals(4, actual.size());
    }

    @Test
    @DisplayName("find customer by criteria username is milli should found only milli")
    public void find_customer_by_criteria_has_username() {
        CustomerSearchCriteria criteria = new CustomerSearchCriteria();
        criteria.setUserName("milli");
        List<Customer> actual = customerRepository.findAll(CustomerSpecification.searchByCriteria(criteria));
        assertEquals(1, actual.size());
    }

    @Test
    @DisplayName("find customer by criteria age is twenty five should found only violette")
    public void find_customer_by_criteria_has_age() {
        CustomerSearchCriteria criteria = new CustomerSearchCriteria();
        criteria.setAge(25);
        List<Customer> actual = customerRepository.findAll(CustomerSpecification.searchByCriteria(criteria));
        assertEquals(1, actual.size());
    }

    @Test
    @DisplayName("find customer by criteria username is ink and age is twenty should found only ink")
    public void find_customer_by_criteria_username_and_age() {
        CustomerSearchCriteria criteria = new CustomerSearchCriteria();
        criteria.setUserName("ink");
        criteria.setAge(20);
        List<Customer> actual = customerRepository.findAll(CustomerSpecification.searchByCriteria(criteria));
        assertEquals(1, actual.size());
    }


}
