 list of apis created
 1. create_account: a customer can create an account with firstname, lastname, email, password
 - POST: http://localhost:8080/api/customer/registration
  
 2. login: a customer can login with email and password and a Bearer token will be sent in the headers which 
   wil be used for Authentication and  Authorization.
   - POST: http://localhost:8080/auth/login

 3. Find a customer: get a customer details from the database by providing the email of the customer
  - GET: http://localhost:8080/api/customer/find-customer-by-email/{email}

 4.  3. Find all customer: get all customer details from the database
  - GET: http://localhost:8080/api/customer/find-all-customer