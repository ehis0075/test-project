package klasha.store.KlashaCourier.controller;



import klasha.store.KlashaCourier.dto.CustomerRegistrationDto;
import klasha.store.KlashaCourier.models.Customer;
import klasha.store.KlashaCourier.service.CustomerService;
import klasha.store.KlashaCourier.service.exception.CustomerAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("api/customer")
@Slf4j
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/registration")
    ResponseEntity<?> registration(@RequestBody CustomerRegistrationDto registrationDto){

        log.info("Customer registration request ---> {}", registrationDto);

        try {
            customerService.register_customer(registrationDto);
        } catch (CustomerAlreadyExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok().body("registration successful");
    }

    @GetMapping("/get-logged-in-customer")
    public ResponseEntity<Customer> getLoggedInUser(){

        log.info("Get logged in user called");
        Customer customer = customerService.getLoggedInUser();

        log.info("Object found --> {}", customer);
        return ResponseEntity.ok().body(customer);
    }

    @GetMapping("/find-customer-by-email/{email}")
    ResponseEntity<?> getCustomer(@PathVariable String email){

        log.info("request to get a customer with email = "+ email);

        Customer customer;

        try {
           customer = customerService.findCustomerByEmail(email);
        } catch(ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok().body(customer);

    }

    @GetMapping("/find-all-customers")
    ResponseEntity<?> getAllCustomers(){

        log.info(" ------- request to get all customers  -------");

        List<Customer> customers;

        try {
            customers = customerService.findAllCustomers();
        } catch(ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.ok().body(customers);

    }

}
