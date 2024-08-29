package hmart.customer.repository;

import hmart.customer.domain.Customer;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaCustomerRepository implements CustomerRepository {
    private final EntityManager em;  // DI
    public JpaCustomerRepository(EntityManager em) {
        this.em = em;
    }
    @Override
    public Customer save(Customer customer) {
        em.persist(customer);
        return customer;
    }
    @Override
    public Optional<Customer> findById(String id) {
        Customer customer = em.find(Customer.class, id);
        return Optional.ofNullable(customer);
    }
    public List<Customer> findAll() {
        return em.createQuery("select m from Customer m", Customer.class)
                .getResultList();
    }
    public Optional<Customer> findByName(String name) {
        List<Customer> result = em.createQuery(
                        "select m from Customer m where m.name = :name",Customer.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }
}