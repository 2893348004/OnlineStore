package com.OnlineStore.OnlineStoreFrontEnd.Checkout;


import com.OnlineStore.OnlineStoreCommon.Entity.CartItem;
import com.OnlineStore.OnlineStoreCommon.Entity.Customer;
import com.OnlineStore.OnlineStoreCommon.Entity.Orders;
import com.OnlineStore.OnlineStoreFrontEnd.Customer.CustomerService;
import com.OnlineStore.OnlineStoreFrontEnd.Customer.CustomerUserDetails;
import com.OnlineStore.OnlineStoreFrontEnd.Orders.OrderService;
import com.OnlineStore.OnlineStoreFrontEnd.Orders.OrdersRepository;
import com.OnlineStore.OnlineStoreFrontEnd.ShoppingCart.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CheckOutController {

@Autowired
private CustomerService customerService;
@Autowired
private ShoppingCartService shoppingCartService;
@Autowired
private OrdersRepository ordersRepository;

@Autowired
private OrderService orderService;



@GetMapping("/checkout")
public String checkoutPage(@AuthenticationPrincipal CustomerUserDetails userDetails,
                           Model model
                           ) {

    Integer customerId = userDetails.getIds();
    Customer customer =  customerService.get(customerId);
    String address = customer.getFullAddress();
    List<CartItem> cartItemList = shoppingCartService.cartItemList(customer);

    double tax =  0.15;

    float estimatedTotal = 0.0F;
    for(CartItem item: cartItemList){
        estimatedTotal += item.subtotal();
    }

    model.addAttribute("estimatedTotal", estimatedTotal);
    model.addAttribute("address",address);
    model.addAttribute("cartItemList",cartItemList);
    model.addAttribute("salesTax",tax);




    return "checkout/checkout";
}

    @RequestMapping("/confirm_order")
    public String checkoutConfirm(@AuthenticationPrincipal CustomerUserDetails userDetails,
                               Model model, RedirectAttributes redirectAttributes
    ) {
        Integer customerId = userDetails.getIds();
        Customer customer =  customerService.get(customerId);
        String address = customer.getFullAddress();
        List<CartItem> cartItemList = shoppingCartService.cartItemList(customer);



        StringBuilder items = new StringBuilder();
        for(CartItem cartItem: cartItemList){
            var x =cartItem.toString();
            items.append(x);
        }


        var time = java.time.LocalDateTime.now();

        var orderId = orderService.saveThisOrderReturnOrderId(customerId, time, items.toString());


        model.addAttribute("message", " Order " + orderId + " has been completed, you may come to the store for pickup");
        model.addAttribute("address", address );


        shoppingCartService.clear(customerId);


        return "checkout/Order_completed";
    }




}
