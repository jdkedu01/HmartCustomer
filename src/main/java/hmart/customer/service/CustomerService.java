package hmart.customer.service;

import hmart.customer.domain.Customer;
import hmart.customer.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    //@Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    // 회원가입
    public String join(Customer customer) {
        validateDuplicateMember(customer); //중복 회원 검증
        customerRepository.save(customer);
        return customer.getId();
    }
    private void validateDuplicateMember(Customer customer) {
        customerRepository.findById(customer.getId())
                .ifPresent(m -> {throw new IllegalStateException("이미 존재하는 ID 회원입니다.");});
    }
    // 회원 조회
    public List<Customer> findMembers() {
        // 여기서도 별도의 조건이 있으면 코드로 표현함. 예를 들어 현재의 리소스상황에따라 상위 N개로 제한할 수 있음
        return customerRepository.findAll();
    }
    public Optional<Customer> findOne(String memberId) {
        return customerRepository.findById(memberId);
    }
}




