package hmart.customer;
import hmart.customer.domain.Customer;
import hmart.customer.repository.CustomerRepository;
import hmart.customer.repository.MemoryCustomerRepository;
import hmart.customer.service.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration  // Configuraion도 spring bean으로 관리됨. DI
public class SpringConfig {
    @Bean  // 아래 메서드의 반환 객체가 auto DI
    public CustomerService customerService() {
        return new CustomerService(customerRepository());
    }
    @Bean
    public CustomerRepository customerRepository() {
        return new MemoryCustomerRepository();
    }
}










