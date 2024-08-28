package hmart.customer.repository;
import hmart.customer.domain.Customer;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public class MemoryCustomerRepository implements CustomerRepository
{
    private static Map<String, Customer> store = new HashMap<>();
    @Override
    public Customer save(Customer customer) {
        store.put(customer.getId(), customer);
        return customer;
    }
    @Override
    public Optional<Customer> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(store.values());
    }
    @Override
    public Optional<Customer> findByName(String name) {
        return store.values().stream()
                .filter(customer -> customer.getName().equals(name)).findAny();
    }
    public void clearStore() {
        store.clear();
    }
}