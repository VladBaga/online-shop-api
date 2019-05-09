package org.fasttrackit.onlineshopapi.steps;

import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.service.CustomerService;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerSteps {

    @Autowired
    private CustomerService customerService;

    public Customer createCustomer() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFirstName("Vlad");
        request.setLastName("Pop");
        request.setAddress("Memorandumului 10");
        request.setEmail("xyz@yahoo.com");
        request.setPhone("0747176245");

        return customerService.createCustomer(request);
    }
}
