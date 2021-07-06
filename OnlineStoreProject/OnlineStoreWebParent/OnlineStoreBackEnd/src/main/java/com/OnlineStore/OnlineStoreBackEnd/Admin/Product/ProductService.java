package com.OnlineStore.OnlineStoreBackEnd.Admin.Product;



import com.OnlineStore.OnlineStoreBackEnd.ProductRepository;

import com.OnlineStore.OnlineStoreCommon.Entity.Product;
import com.OnlineStore.OnlineStoreCommon.Exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public Product get(Integer id) throws ProductNotFoundException {
        try{
            return productRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new ProductNotFoundException("Could not find any Product with ID" + id);
        }
    }

    public List<Product> listAll(){
        return (List<Product>) productRepository.findAll();
    }

    public Product save(Product product){
        if(product.getId() == null){
            product.setCreateTime(new Date());
        }
        if(product.getAlias() == null || product.getAlias().isEmpty()){
            String defaultAlias = product.getName().replaceAll(" ", "-");
            product.setAlias(defaultAlias);
        }else{
            product.setAlias(product.getAlias().replaceAll(" ", "-"));
        }

        product.setUpdateTime(new Date());

        if((product.getId() != null) && (product.getMainImage() == null)){
            Product productInDB = productRepository.findById(product.getId()).get();
            var x = productInDB.getMainImage();
            product.setMainImage(x);
        }

        return productRepository.save(product);
    }


    public boolean isProductUnique(Integer id, String name){
     Product product =   productRepository.findProdByName(name);
     if(product == null) return true;

     boolean isThisNewProd = (id == null);
     if(isThisNewProd){
         if(product != null) return false;
     }else{
         if(product.getId() != id) return false;
     }

     return true;
    }


    public void delete(Integer id) throws ProductNotFoundException{
        Product product = productRepository.findById(id).get();

        if(product == null ){
            throw new ProductNotFoundException("Could not  find any product with ID: " +id);
        }
        productRepository.deleteById(id);
    }








}
