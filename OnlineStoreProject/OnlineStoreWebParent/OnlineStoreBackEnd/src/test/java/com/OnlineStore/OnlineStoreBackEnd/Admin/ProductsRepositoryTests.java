package com.OnlineStore.OnlineStoreBackEnd.Admin;


import com.OnlineStore.OnlineStoreBackEnd.ProductRepository;
import com.OnlineStore.OnlineStoreCommon.Entity.Category;
import com.OnlineStore.OnlineStoreCommon.Entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductsRepositoryTests {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    public void testCreateProduct(){
        Category category = testEntityManager.find(Category.class, 1);

        Product product = new Product();
        product.setName("Dell Supercomputer");
        product.setShortDescription("SUPER powerful computer good at large calculations");
        product.setFullDescription("SUPER powerful computer good at large calculations, full description is much longer");
        product.setAlias("dell super comp");

        product.setPrice(5000);
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());

        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isEqualTo(1);

    }



    @Test
    public void testCreateProduct1(){
        Category category = testEntityManager.find(Category.class, 4);

        Product product = new Product();
        product.setName("HP Hardware Component 123");
        product.setShortDescription("HP Hardware Component 123, replacement for component 123 on HP desktop");
        product.setFullDescription("allow your HP desktop to run longer by replacing  component 123 for smoother performance and better memory usage");
        product.setAlias("hw  comp 123");

        product.setPrice(2000);
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());

        product.setCategory(category);

        Product savedProduct = productRepository.save(product);



    }

    @Test
    public void testCreateProduct2(){
        Category category = testEntityManager.find(Category.class, 4);

        Product product = new Product();
        product.setName("USB-C to HDMI Adapter");
        product.setShortDescription("USB-C to HDMI Adapter, 4K CLDAY USB Type-C");
        product.setFullDescription("USB-C to HDMI Adapter, 4K CLDAY USB Type-C high performance device");
        product.setAlias("USB-C to HDMI");

        product.setPrice(100);
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());

        product.setCategory(category);

        Product savedProduct = productRepository.save(product);



    }

    @Test
    public void testCreateProduct3(){
        Category category = testEntityManager.find(Category.class, 4);

        Product product = new Product();
        product.setName("Intel Core i9-9900KF");
        product.setShortDescription("Intel Core");
        product.setFullDescription("Intel Core slightly used but still in good condition");
        product.setAlias("Intel Core Blaster 1");

        product.setPrice(190);
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());

        product.setCategory(category);

        Product savedProduct = productRepository.save(product);


    }

    @Test
    public void testCreateProduct4(){
        Category category = testEntityManager.find(Category.class, 5);

        Product product = new Product();
        product.setName("Corsair Vengeance LPX 32GB");
        product.setShortDescription("Corsair Vengeance");
        product.setFullDescription("Corsair Vengeance LPX 32GB memory for work devices");
        product.setAlias("corsair  LPX 32GB");

        product.setPrice(150);
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());

        product.setCategory(category);

        Product savedProduct = productRepository.save(product);



    }











    @Test
    public void testListProducts(){

        Iterable<Product> iterableProd = productRepository.findAll();

        iterableProd.forEach((System.out::println));


    }


    @Test
    public void testUpdateProducts1(){
        Integer id = 1;
        Product product = productRepository.findById(id).get();
        product.setName("Dell Supercomputer");

        productRepository.save(product);


    }

    @Test
    public void testUpdateProducts(){
        Integer id = 1;
        Product product = productRepository.findById(id).get();
        product.setPrice(499);

        productRepository.save(product);

        Product updatedProd = testEntityManager.find(Product.class, id);

        assertThat(updatedProd.getPrice()).isEqualTo(499);

    }

    @Test
    public void testDeleteProducts(){
    Integer id = 3;
    productRepository.deleteById(id);

    Optional<Product> result =  productRepository.findById(id);

    assertThat(!result.isPresent());
    }




}
