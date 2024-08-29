package hmart.customer;
import hmart.customer.domain.Customer;
import hmart.customer.repository.*;
import hmart.customer.service.CustomerService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration  // Configuraion도 spring bean으로 관리됨. DI
public class SpringConfig {
//    private final DataSource dataSource;  // for JDBC, JdbcTemplate
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;}

    private final EntityManager em;    // for JPA
    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;}
    @Bean  // 아래 메서드의 반환 객체가 auto DI
    public CustomerService customerService() {
        return new CustomerService(customerRepository());
    }
    @Bean
    public CustomerRepository customerRepository() {
//        return new MemoryCustomerRepository();
//        return new JdbcCustomerRepository(dataSource);
//        return new JdbcTemplateCustomerRepository(dataSource);
        return new JpaCustomerRepository(em);
    }
}





