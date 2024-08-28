package hmart.customer.repository;

import hmart.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(String id);
    Optional<Customer> findByName(String name);
    List<Customer> findAll();
}
