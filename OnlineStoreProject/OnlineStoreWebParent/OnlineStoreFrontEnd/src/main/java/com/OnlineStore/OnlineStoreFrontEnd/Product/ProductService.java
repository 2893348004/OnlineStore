package com.OnlineStore.OnlineStoreFrontEnd.Product;

import com.OnlineStore.OnlineStoreCommon.Entity.Product;
import com.OnlineStore.OnlineStoreCommon.Exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public static final int PRODUCTS_PER_PAGE = 10;

@Autowired
private ProductRepository productRepository;


public Page<Product> listCategory(int pageNum, Integer categoryId){

    String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
    Pageable pageable = PageRequest.of(pageNum -1, PRODUCTS_PER_PAGE);

    return productRepository.listByCategory(categoryId, categoryIdMatch, pageable);
}

public Product getProduct(String alias) throws ProductNotFoundException {
    Product product = productRepository.findByAlias(alias);

    if(product == null){
         throw new ProductNotFoundException("Could not find product with this alias: " + alias);

    }

    return product;
}

    public Product getProductId(Integer productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId).get();

        if(product == null){
            throw new ProductNotFoundException("Could not find product with this ID: " + productId);

        }

        return product;
    }



}
