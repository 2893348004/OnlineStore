package com.OnlineStore.OnlineStoreFrontEnd;


import com.OnlineStore.OnlineStoreCommon.Entity.CartItem;
import com.OnlineStore.OnlineStoreCommon.Entity.Customer;
import com.OnlineStore.OnlineStoreCommon.Entity.Product;
import com.OnlineStore.OnlineStoreFrontEnd.CartItem.CartItemRepository;
import com.OnlineStore.OnlineStoreFrontEnd.Orders.OrdersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartItemTesting {

@Autowired
private CartItemRepository cartItemRepository;

@Autowired
private TestEntityManager entityManager;

@Autowired
private OrdersRepository ordersRepository;

@Test
public void testSaveItem(){
    Integer customerId = 1;
    Integer productId = 1;


    Customer customer = entityManager.find(Customer.class, customerId);
    Product product = entityManager.find(Product.class, productId);

    CartItem newItem = new CartItem();
    newItem.setCustomer(customer);
    newItem.setProduct(product);
    newItem.setQuantity(1);

    CartItem savedItem = cartItemRepository.save(newItem);

    assertThat(savedItem.getId()).isGreaterThan(0);

    
}

@Test
public  void testFindByCustomer(){

    Integer customerId = 1;
    Integer productId1 = 1;
    Integer productId2 = 2;

    Integer customerId2 = 2;
    Integer productId3 = 3;


    Customer customer1 = entityManager.find(Customer.class, customerId);
    Customer customer2 = entityManager.find(Customer.class, customerId2);

    Product product1 = entityManager.find(Product.class, productId1);
    Product product2 = entityManager.find(Product.class, productId2);
    Product product3 = entityManager.find(Product.class, productId3);


    CartItem newItem = new CartItem();
    newItem.setCustomer(customer1);
    newItem.setProduct(product1);
    newItem.setQuantity(1);

    CartItem newItem2 = new CartItem();
    newItem.setCustomer(customer1);
    newItem.setProduct(product2);
    newItem.setQuantity(1);

    CartItem newItem3 = new CartItem();
    newItem.setCustomer(customer2);
    newItem.setProduct(product3);
    newItem.setQuantity(1);


    CartItem secondCustomerCart = cartItemRepository.save(newItem3);
    Iterable<CartItem> iterableFirstCustomerCart = cartItemRepository.saveAll(List.of(newItem, newItem2));

    assertThat(iterableFirstCustomerCart).size().isEqualTo(2);
    assertThat(secondCustomerCart.getQuantity()).isGreaterThan(0);

}

    @Test
    public void testFindByCustomerAndProduct(){
        Integer customerId = 1;
        Integer productId = 1;

        CartItem item = cartItemRepository.findByCustomerAndProduct(new Customer(), new Product());

        assertThat(item).isNotNull();

        System.out.println(item);
    }












}





