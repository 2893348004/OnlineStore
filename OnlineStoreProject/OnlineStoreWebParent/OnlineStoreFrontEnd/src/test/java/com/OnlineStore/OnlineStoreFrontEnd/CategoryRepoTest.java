package com.OnlineStore.OnlineStoreFrontEnd;

import com.OnlineStore.OnlineStoreCommon.Entity.Category;
import com.OnlineStore.OnlineStoreCommon.Entity.Customer;
import com.OnlineStore.OnlineStoreFrontEnd.Category.CategoryRepository;
import com.OnlineStore.OnlineStoreFrontEnd.Customer.CustomerRepository;
import com.OnlineStore.OnlineStoreFrontEnd.Customer.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepoTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;
/*
    @Autowired
    private PasswordEncoder passwordEncoder;

*/

    @Test
    public void testFindByCategory2(){
      /*  var y = customerService.get(1);
        var z = y.getPassword();*/

        var y = customerRepository.findById(1).get();
        y.setPassword("defaultcustomer2021");
        customerService.save(y);
        //String encodedPassword = passwordEncoder.encode(y.getPassword());
       // y.setPassword(encodedPassword);


        var z = y.getPassword();

        System.out.println(z);

    }

    @Test
    public void testFindByCategory(){
           List<Category> x = categoryRepository.findAllEnabled();
        x.forEach( category -> {System.out.println(category.getName() + "(" + category.isEnabled() + ")");
        });

    }


    @Test
    public void testFindByAliasCategory(){
        List<Category> x = categoryRepository.findAllEnabled();
        x.forEach( category -> {System.out.println(category.getName() + "(" + category.isEnabled() + ")");
        });

    }
}