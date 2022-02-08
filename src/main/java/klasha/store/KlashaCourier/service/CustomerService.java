package klasha.store.KlashaCourier.service;


import klasha.store.KlashaCourier.dto.CustomerRegistrationDto;
import klasha.store.KlashaCourier.models.Customer;
import klasha.store.KlashaCourier.service.exception.CustomerAlreadyExistException;

import java.util.List;


public interface CustomerService {

    void register_customer(CustomerRegistrationDto registrationDto) throws CustomerAlreadyExistException;

    public Customer getLoggedInUser();

    Customer findCustomerByEmail(String email);

    List<Customer> findAllCustomers();

}
