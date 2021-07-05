package com.OnlineStore.OnlineStoreFrontEnd.Customer;


import com.OnlineStore.OnlineStoreCommon.Entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;




    public Customer get(Integer id) {
            return customerRepository.findById(id).get();

    }



    public boolean isEmailUnique( String email){
        Customer customer = customerRepository.findByEmail(email);
        return  customer == null;


        }


    public void save(Customer customerInForm){
        if(!customerInForm.getPassword().isEmpty()){
            String encodedPassword = passwordEncoder.encode(customerInForm.getPassword());
            customerInForm.setPassword(encodedPassword);
        }else{
            Customer customerInDB = customerRepository.findById(customerInForm.getId()).get();
            customerInForm.setPassword(customerInDB.getPassword());
        }
        customerRepository.save(customerInForm);
    }




}
