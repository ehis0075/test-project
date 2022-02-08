package klasha.store.KlashaCourier.service;



import klasha.store.KlashaCourier.dto.CustomerRegistrationDto;
import klasha.store.KlashaCourier.models.*;
import klasha.store.KlashaCourier.repository.*;
import klasha.store.KlashaCourier.security.authfacade.AuthenticationFacade;
import klasha.store.KlashaCourier.service.exception.CustomerAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    AuthenticationFacade authenticationFacade;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Override
    public void register_customer(CustomerRegistrationDto registrationDto) throws CustomerAlreadyExistException {

        if(customerRepository.findByEmail(registrationDto.getEmail()) == null){

            AppUser appUser = new AppUser();
            appUser.setEmail(registrationDto.getEmail());
            appUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            appUser.addRole(Role.CUSTOMER);
            appUser.setIsVerified(true);

            appUserRepository.save(appUser);

            registrationDto.setPassword(appUser.getPassword());

            Customer customer = new Customer();
            customer.setFirstName(registrationDto.getFirstName());
            customer.setLastName(registrationDto.getLastName());
            customer.setEmail(appUser.getEmail());
            customer.setPassword(appUser.getPassword());
            customer.setRoles(List.of(appUser.getRoles().toArray(new Role[0])));
            customer.setAppUser(appUser);

            // set billing details
//            BillingDetails billingDetails = new BillingDetails();
//            billingDetails.setAccountNumber();   //generate unique account number
//            billingDetails.setTariff("");



            customerRepository.save(customer);

            log.info("After saving customer details to db --->{}", customer);


        } else {
            throw new CustomerAlreadyExistException("a customer with email "+ registrationDto.getEmail() +" already exist!");
        }

    }

    @Override
    public Customer getLoggedInUser() {

        String name = authenticationFacade.getAuthentication().getName();
        log.info("Authentication facade --> {}", name);

        return customerRepository.findByEmail(name);
    }

    @Override
    public Customer findCustomerByEmail(String email) {

        Customer savedCustomer = customerRepository.findByEmail(email);

        if(savedCustomer == null){

            throw new ResourceNotFoundException("customer with "+ email + " cannot be found!");
        }

        log.info("customer returned from db ---->"+ savedCustomer);
        return savedCustomer;

    }

    @Override
    public List<Customer> findAllCustomers() {

        List<Customer> customers = customerRepository.findAll();

        if(customers == null){
            throw new  ResourceNotFoundException("No customer was found in the db");
        }

        log.info("list of customer returned from db ---->"+ customers);
        return customers;
    }

}
