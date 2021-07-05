package com.OnlineStore.OnlineStoreBackEnd.Admin.Customer;


import com.OnlineStore.OnlineStoreCommon.Entity.Customer;
import com.OnlineStore.OnlineStoreCommon.Exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CustomerService {
    public static final int CUSTOMERS_PER_PAGE = 10;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Page<Customer> listByPage(int pageNum, String sortField, String sortDir, String keyword){

        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1, CUSTOMERS_PER_PAGE, sort);

        if(keyword != null){
            return customerRepository.findAll(keyword, pageable);
        }

        return customerRepository.findAll(pageable);
    }


    public void updateCustomerEnabledStatus(Integer id, boolean enabled){
        customerRepository.updateEnabledStatus(id, enabled);
    }


    public Customer get(Integer id) throws CustomerNotFoundException{
        try{
            return customerRepository.findById(id).get();
        }catch (NoSuchElementException ex){
            throw new CustomerNotFoundException("Could not find customer with the requested Id :" + id);
        }
    }


    private void encodePassword(Customer customer){
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
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

    public void delete(Integer id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id).get();
        if(customer == null){
            throw new CustomerNotFoundException("Could not find customer with the requested Id :" + id);
        }

        customerRepository.deleteById(id);
    }


    public boolean isEmailUnique( String email){
        Customer customer = customerRepository.findByEmail(email);
        return  customer == null;


    }


}
