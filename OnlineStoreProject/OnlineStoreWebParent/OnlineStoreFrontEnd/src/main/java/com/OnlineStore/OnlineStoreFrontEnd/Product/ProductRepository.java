package com.OnlineStore.OnlineStoreFrontEnd.Product;


import com.OnlineStore.OnlineStoreCommon.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository  extends PagingAndSortingRepository<Product, Integer> {

    @Query("SELECT p FROM Product p WHERE p.enabled = true " +
            "AND (p.category.id = ?1 OR  p.category.allParentIds LIKE %?2%)" +
            "ORDER by p.name ASC")
    public Page<Product> listByCategory(Integer categoryId, String categoryIDMatch,
                                        Pageable pageable);


    public Product findByAlias(String alias);


}
