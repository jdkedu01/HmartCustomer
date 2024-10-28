package hmart.customer.controller;

import hmart.customer.domain.Customer;
import hmart.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController
            (CustomerService customerService){
            this.customerService = customerService;
    }
    @GetMapping("/")
    public String home() {
        return "home";
    }
    //Template이 Static을 우선함

    @GetMapping(value = "/customers/new")
    public String createForm22() {
        return "customers/createCustomerForm";
    }

    @PostMapping(value = "/customers/new")
    public String create(Customer form) {
        Customer customer = new Customer();
        customer.setId(form.getId());
        customer.setName(form.getName());
        customer.setAge(form.getAge());
        customer.setLevels(form.getLevels());
        customer.setJob(form.getJob());
        customer.setPoint(form.getPoint());
        customerService.join(customer);
        return "redirect:/";
    }
    @GetMapping(value = "/customers")
    public String list(Model model) {
        List<Customer> customers = customerService.findCustomers();
        model.addAttribute("customers", customers);
        return "customers/customerList";
    }
    @GetMapping(value = "/selectOne")
    public String oneCustomer(@RequestParam("id") String customerId, Model model) {
        Optional<Customer> customer = customerService.findOne(customerId);
        model.addAttribute("customer", customer);
        // localhost:8080/hello-param?name=John     name이라는 변수에 'John'이 할당됨
        // model.addAttribute('name', name) : 앞 name은 아래 html에서 사용될 변수 이름
        return "customers/selectOne";
    }
}