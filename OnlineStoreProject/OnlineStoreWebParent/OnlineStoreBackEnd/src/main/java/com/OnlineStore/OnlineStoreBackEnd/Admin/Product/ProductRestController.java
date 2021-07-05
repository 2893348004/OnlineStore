package com.OnlineStore.OnlineStoreBackEnd.Admin.Product;


import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {

    private ProductService productService;

    @PostMapping("/products/check_product")
    public String checkDupesProducts(@Param("id") Integer id, @Param("name") String name){
    return productService.isProductUnique(id,name) ?  "OK" : "Duplicated";
    }


}
