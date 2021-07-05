package com.OnlineStore.OnlineStoreBackEnd.Admin.Category.Controllers;


import com.OnlineStore.OnlineStoreBackEnd.Admin.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryRestController {




    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categories/check_category")
    public String checkDuplicateCategory(@Param("id") Integer id, @Param("name") String name){
        return categoryService.isCategoryUnique(id, name) ? "OK" : "Duplicated";
    }






}
