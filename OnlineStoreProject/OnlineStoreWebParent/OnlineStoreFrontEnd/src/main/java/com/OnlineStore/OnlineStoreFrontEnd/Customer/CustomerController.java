package com.OnlineStore.OnlineStoreFrontEnd.Customer;


import com.OnlineStore.OnlineStoreCommon.Entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerController {


    @Autowired
    private CustomerService customerService;

    @GetMapping("/register")
    public String registerCustomer(Model model){

        model.addAttribute("pageTitle", "Customer Registration" );
        model.addAttribute("customer", new Customer());

        return "register/register_form";
    }



    @PostMapping("/create_customer")
    public String saveUser(Customer customer,  RedirectAttributes redirectAttributes)  {

        customer.setEnabled(true);
        customer.setCreatedTime(java.time.LocalDateTime.now());
        customerService.save(customer);
       redirectAttributes.addFlashAttribute("passmessage", "Successful creation of new User");
        return "redirect:/home";

    }




    @GetMapping("/customers/edit/")
    public String editUser(@AuthenticationPrincipal CustomerUserDetails userDetails,
                           Model model
                        , RedirectAttributes redirectAttributes){
        Integer id = userDetails.getIds();
        Customer customer = customerService.get(id);
        model.addAttribute("customer", customer);
        model.addAttribute("pageTitle", "Edit customer (ID: "+ id + "  )");


        return "register/update_customer_form";

    }

    @PostMapping("/update_customer")
    public String updateUser(Customer customer,  RedirectAttributes redirectAttributes)  {

        customer.setEnabled(true);
        customer.setCreatedTime(java.time.LocalDateTime.now());
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("passmessage", "Successful edit of your information");
        return "redirect:/home";

    }














}
