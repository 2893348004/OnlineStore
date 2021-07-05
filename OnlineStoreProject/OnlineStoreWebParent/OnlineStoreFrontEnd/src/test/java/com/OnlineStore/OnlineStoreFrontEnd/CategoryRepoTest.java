package com.OnlineStore.OnlineStoreFrontEnd;

import com.OnlineStore.OnlineStoreCommon.Entity.Category;
import com.OnlineStore.OnlineStoreFrontEnd.Category.CategoryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryRepoTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testFindByCategory(){
           List<Category> x = categoryRepository.findAllEnabled();
        x.forEach( category -> {System.out.println(category.getName() + "(" + category.isEnabled() + ")");
        });

    }


    @Test
    public void testFindByAliasCategory(){
        List<Category> x = categoryRepository.findAllEnabled();
        x.forEach( category -> {System.out.println(category.getName() + "(" + category.isEnabled() + ")");
        });

    }
}