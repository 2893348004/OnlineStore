package com.OnlineStore.OnlineStoreBackEnd;


import com.OnlineStore.OnlineStoreBackEnd.Admin.Category.CategoryRepository;
import com.OnlineStore.OnlineStoreCommon.Entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;


import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;



    @Test
    public void createNewCatjegory(){




       // Category savedCategory = categoryRepository.save(category1);



    }

    @Test
    public void createNewCategory(){
        Category category1 = new Category("Electronics");
        category1.setAlias("Electronics");
        category1.setEnabled(true);


        Category savedCategory = categoryRepository.save(category1);

        assertThat(savedCategory.getId()).isGreaterThan(0);

    }


    @Test
    public void createNewSubCategory(){
        Category parent = categoryRepository.findById(1).get();
        // id 1 is for computers
        Category one = new Category("Desktops", parent);
        Category two = new Category("Laptops", parent);
        Category three = new Category("Computer Components", parent);
        categoryRepository.saveAll(List.of(one,two,three)) ;
    }

    @Test
    public void createNestedSubCategories(){
        Category parent = categoryRepository.findById(4).get();
        // id 5 is for computer compnents
        Category one = new Category("Memory", parent);
        Category savedCategory = categoryRepository.save(one) ;

        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void getMeACategoryAndItsChildren(){
        Category category = categoryRepository.findById(1).get();
        System.out.println(category.getName());

        Set<Category> children = category.getChildren();
        for(Category childCategory: children){
            System.out.println(childCategory.getName());
        }
        assertThat(children.size()).isGreaterThan(0);
    }

    @Test
    public void testPrintedHierarchialCategories(){
        Iterable<Category> categories = categoryRepository.findAll();

        for(Category category : categories){

            if(category.getParent() == null){
                System.out.println(category.getName());
            }else{
                System.out.println(category.getName());
                Set<Category> children = category.getChildren();

                for(Category subCategory: children){
                    System.out.println("--" + subCategory.getName());
                }
            }
        }
    }



    @Test
    public void searchCategoryByName(){
        String name = "Computers";

        Category categoryByName = categoryRepository.findCategoryByName(name);

        System.out.println( "ID by name : " + categoryByName.getId());

        assertThat(categoryByName).isNotNull();
        assertThat(categoryByName.getName()).isEqualTo(name);

    }


    @Test
    public void searchCategoryByAlias(){
        String alias = "electronics";

        Category categoryByAlias = categoryRepository.findCategoryByAlias(alias);
        System.out.println("ID by alias : " + categoryByAlias.getId());

        assertThat(categoryByAlias).isNotNull();
        assertThat(categoryByAlias.getAlias()).isEqualTo(alias);

    }


    @Test
    public void getMeACategory(){
        Category category = categoryRepository.findById(1).get();
        System.out.println(category.getName());

        Set<Category> children = category.getChildren();
        for(Category childCategory: children){
            System.out.println(childCategory.getName());
        }
        assertThat(children.size()).isGreaterThan(0);
    }

}
