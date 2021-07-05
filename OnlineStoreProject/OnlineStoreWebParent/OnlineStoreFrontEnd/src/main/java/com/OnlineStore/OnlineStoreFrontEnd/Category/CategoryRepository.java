package com.OnlineStore.OnlineStoreFrontEnd.Category;

import com.OnlineStore.OnlineStoreCommon.Entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CategoryRepository<C, I extends Number> extends CrudRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE  c.enabled = true ORDER BY c.name ASC")
    public List<Category> findAllEnabled();

    @Query("SELECT c FROM Category c WHERE c.enabled = true AND c.alias = ?1")
    public Category findByAliasEnabled(String alias);
}
