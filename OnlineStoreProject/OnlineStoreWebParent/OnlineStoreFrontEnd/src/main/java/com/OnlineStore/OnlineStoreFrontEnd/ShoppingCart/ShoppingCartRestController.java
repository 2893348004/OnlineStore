package com.OnlineStore.OnlineStoreFrontEnd.ShoppingCart;


import com.OnlineStore.OnlineStoreCommon.Entity.Customer;
import com.OnlineStore.OnlineStoreFrontEnd.Customer.CustomerService;
import com.OnlineStore.OnlineStoreFrontEnd.Customer.CustomerUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingCartRestController {


@Autowired
 private ShoppingCartService shoppingCartService;
@Autowired
private CustomerService customerService;

@PostMapping("/cart/add/{productId}/{quantity}")
public String addProductToCart(@PathVariable(name="productId") Integer productId,
                               @PathVariable(name="quantity") Integer quantity,
                               @AuthenticationPrincipal CustomerUserDetails userDetails
                               ){
    Integer id = userDetails.getIds();
    Customer customer = customerService.get(id);
    Integer updatedQuantity = shoppingCartService.addProduct(productId,quantity,customer);


    return updatedQuantity + "item(s) of this product were added to your shopping cart";

}

    @PostMapping("/cart/update/{productId}/{quantity}")
    public String updateQuantity(@PathVariable(name="productId") Integer productId,
                                   @PathVariable(name="quantity") Integer quantity,
                                   @AuthenticationPrincipal CustomerUserDetails userDetails
    ){
        Integer id = userDetails.getIds();
        Customer customer = customerService.get(id);
        float subtotal = shoppingCartService.updateQuantity(productId,quantity,customer);


        return String.valueOf(subtotal) ;

    }

    @DeleteMapping("/cart/remove/{productId}")
    public String removeThisProduct(@PathVariable("productId") Integer productId,
                                    @AuthenticationPrincipal CustomerUserDetails userDetails){

        Integer id = userDetails.getIds();
        Customer customer = customerService.get(id);

    return "";
    }





}
