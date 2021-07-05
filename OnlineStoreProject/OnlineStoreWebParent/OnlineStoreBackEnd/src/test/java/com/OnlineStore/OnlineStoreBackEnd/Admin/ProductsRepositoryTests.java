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
