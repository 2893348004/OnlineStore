package com.OnlineStore.OnlineStoreBackEnd.Admin.Category;

import com.OnlineStore.OnlineStoreCommon.Entity.Category;

import com.OnlineStore.OnlineStoreCommon.Entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category,Integer> {

    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    public List<Category> findRootCategories(Sort sort);


    @Query("SELECT c FROM Category c WHERE c.name = :name")
    public Category findCategoryByName(@Param("name") String name);



    @Query("SELECT c FROM Category c WHERE c.alias = :alias")
    public Category findCategoryByAlias(@Param("alias") String alias);







}
