package com.OnlineStore.OnlineStoreFrontEnd.ShoppingCart;


import com.OnlineStore.OnlineStoreCommon.Entity.CartItem;
import com.OnlineStore.OnlineStoreCommon.Entity.Customer;
import com.OnlineStore.OnlineStoreFrontEnd.Customer.CustomerService;
import com.OnlineStore.OnlineStoreFrontEnd.Customer.CustomerUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ShoppingCartController {


    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private CustomerService customerService;



    @GetMapping("/cart")
    public String viewCart(@AuthenticationPrincipal CustomerUserDetails userDetails,
                           Model model){

        var x = userDetails.getIds();
        Customer customer =  customerService.get(x);
        List<CartItem> cartItemList = shoppingCartService.cartItemList(customer);

        float estimatedTotal = 0.0F;
        for(CartItem item: cartItemList){
            estimatedTotal += item.subtotal();
        }

            model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("estimatedTotal", estimatedTotal);


        return "cart/shopping_cart";
    }

    @GetMapping("/delete/{productId}")
    public String viewCartDelete(@AuthenticationPrincipal CustomerUserDetails userDetails,
                                 @PathVariable(name="productId") Integer productId,
                                 RedirectAttributes redirectAttributes,
                           Model model){

        Integer customerId = userDetails.getIds();
        Customer customer =  customerService.get(customerId);
        shoppingCartService.delete(customerId,productId);

        List<CartItem> cartItemList = shoppingCartService.cartItemList(customer);


        float estimatedTotal = 0.0F;
        for(CartItem item: cartItemList){
            estimatedTotal += item.subtotal();



        }


        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("estimatedTotal", estimatedTotal);
        redirectAttributes.addFlashAttribute("passmessage", "Successful deletion of product ");

      return "cart/shopping_cart";

    }







}

