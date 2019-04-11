package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Customer;
import org.fasttrackit.onlineshopapi.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.CustomerService;
import org.fasttrackit.onlineshopapi.transfer.customer.CreateCustomerRequest;
import org.fasttrackit.onlineshopapi.transfer.customer.UpdateCustomerRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceIntegrationTests {

    @Autowired
    private CustomerService customerService;

    @Test
    public void testCreateCustomer_whenValidRequest_thenReturnCustomerWithId(){ //metodele pt test intotdeauna VOID
        Customer customer = createCustomer();

        assertThat(customer, notNullValue());
        assertThat(customer.getId(), greaterThan(0L));
    }

    private Customer createCustomer() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFirstName("Vlad");
        request.setLastName("Pop");
        request.setAddress("Memorandumului 10");
        request.setEmail("xyz@yahoo.com");
        request.setPhone("0747176245");

        return customerService.createCustomer(request);
    }

    @Test (expected = ResourceNotFoundException.class)
    public void testGetCustomer_whenCustomerNotFound_thenThrowResourceNotFoundException() throws ResourceNotFoundException {
        customerService.getCustomer(0);
    }

    @Test
    public void testUpdateCustomer_whenValidRequestWithAllFields_thenReturnUpdatedCustomer() throws ResourceNotFoundException { //ORICE TEST ESTE INDEPENDENT
        Customer createdCustomer = createCustomer();

        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setFirstName(createdCustomer.getFirstName() + " Edited");
        request.setLastName(createdCustomer.getLastName() + " Edited");
        request.setAddress(createdCustomer.getAddress() + " Edited");
        request.setEmail(createdCustomer.getEmail() + " Edited");
        request.setPhone(createdCustomer.getPhone() + " Edited");

        Customer updatedCustomer = customerService.updateCustomer(createdCustomer.getId(), request);

        assertThat(updatedCustomer.getFirstName(), is(request.getFirstName()));
        assertThat(updatedCustomer.getFirstName(), not(is(createdCustomer.getFirstName())));

        assertThat(updatedCustomer.getLastName(), is(request.getLastName()));
        assertThat(updatedCustomer.getAddress(), is(request.getAddress()));
        assertThat(updatedCustomer.getEmail(), is(request.getEmail()));
        assertThat(updatedCustomer.getPhone(), is(request.getPhone()));

        assertThat(updatedCustomer.getId(), is(createdCustomer.getId()));
    }
    // todo: Implement negative tests for update and tests for update with some fields only.

    @Test (expected = ResourceNotFoundException.class)
    public void testDeleteCustomer_whenExistingId_thenCustomerIsDeleted() throws ResourceNotFoundException {
        Customer createdCustomer = createCustomer();

        customerService.deleteCustomer(createdCustomer.getId());

        customerService.getCustomer(createdCustomer.getId());
    }
}

