package com.OnlineStore.OnlineStoreBackEnd;


import com.OnlineStore.OnlineStoreCommon.Entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends PagingAndSortingRepository <Product, Integer> {


    @Query("SELECT p FROM Product p WHERE p.name = :name")
    public Product findProdByName(@Param("name") String name);




}
