package hmart.customer.controller;

import hmart.customer.domain.Customer;
import hmart.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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
        System.out.println(customers.size());
        System.out.println(customers.getFirst().getAge());

        model.addAttribute("customers", customers);
        return "customers/customerList";
    }
}