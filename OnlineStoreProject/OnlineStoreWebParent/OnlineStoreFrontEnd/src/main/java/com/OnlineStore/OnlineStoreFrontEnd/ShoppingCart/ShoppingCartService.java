package com.OnlineStore.OnlineStoreFrontEnd.ShoppingCart;

import com.OnlineStore.OnlineStoreCommon.Entity.CartItem;
import com.OnlineStore.OnlineStoreCommon.Entity.Customer;
import com.OnlineStore.OnlineStoreCommon.Entity.Product;
import com.OnlineStore.OnlineStoreFrontEnd.CartItem.CartItemRepository;
import com.OnlineStore.OnlineStoreFrontEnd.Customer.CustomerRepository;
import com.OnlineStore.OnlineStoreFrontEnd.Product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ShoppingCartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public Integer addProduct(Integer productId, Integer quantity, Customer customer)  {
        Integer updatedQuantity = quantity;
        Product product = new Product(productId);

        CartItem cartItem = cartItemRepository.findByCustomerAndProduct(customer, product);

        if(cartItem != null){
            updatedQuantity = cartItem.getQuantity() + quantity;


        }else{
             cartItem = new CartItem();
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
        }
        cartItem.setQuantity(updatedQuantity);

        cartItemRepository.save(cartItem);


        return updatedQuantity;
    }

    public List<CartItem> cartItemList(Customer customer){
        return cartItemRepository.findByCustomer(customer);
    }

    public float updateQuantity(Integer productId, Integer quantity, Customer customer) {
        cartItemRepository.updateQuantity(quantity,customer.getId(),productId);
        Product product = productRepository.findById(productId).get();

        float subtotal = (product.getPrice() - ((product.getDiscountPercent()/100)*product.getPrice())) * quantity;
        return subtotal;
    }

    public void delete(Integer customerId, Integer productId){
        cartItemRepository.deleteByCustomerAndProduct(customerId, productId);
    }

    public void clear( Integer customerId ){
        cartItemRepository.deleteByCustomerId(customerId);

    }

}
