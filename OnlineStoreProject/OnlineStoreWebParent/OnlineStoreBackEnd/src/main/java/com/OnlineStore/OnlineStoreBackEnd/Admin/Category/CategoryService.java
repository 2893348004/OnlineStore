package com.OnlineStore.OnlineStoreBackEnd.Admin.Category;


import com.OnlineStore.OnlineStoreCommon.Exception.CategoryNotFoundException;
import com.OnlineStore.OnlineStoreCommon.Entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

@Autowired
    private CategoryRepository categoryRepository;

public List<Category> noHierarchyListAll(){
    return (List<Category>)  categoryRepository.findAll(Sort.by("name").ascending());
}

public List<Category> listAll(){
    List<Category> rootCategories = categoryRepository.findRootCategories(Sort.by("name").ascending());

    return listHiearchichalCategories(  rootCategories);
}

private List<Category> listHiearchichalCategories( List<Category> rootCategories){
    List<Category> hiearchichalCategories = new ArrayList<>();

    for (Category rootCategory : rootCategories){
        hiearchichalCategories.add(Category.copyFull(rootCategory));

        Set<Category> children = categorySortedSetSubcategory(rootCategory.getChildren());

        for(Category subCategory : children){

             String name = ("--"  +  subCategory.getName());
            hiearchichalCategories.add(Category.copyFull(subCategory, name));

            listSubHierarchialCategories(hiearchichalCategories, subCategory, 1);

        }
    }

    return hiearchichalCategories;
}

private void listSubHierarchialCategories (List<Category> hiearchichalCategories , Category parent, int subLevel ){
    Set<Category> children = categorySortedSetSubcategory(parent.getChildren());
    int newSublevel = subLevel + 1;

    for(Category subCategory: children){
        String name ="";
       for(int i = 0; i < newSublevel ; i++){
            name += "--";
        }
       name += subCategory.getName();
        hiearchichalCategories.add(Category.copyFull(subCategory, name));

        listSubHierarchialCategories(hiearchichalCategories ,  subCategory,  newSublevel  );
    }
}

public Category get(Integer id) throws CategoryNotFoundException {
    try{
        return categoryRepository.findById(id).get();
    } catch (NoSuchElementException ex) {
        throw new CategoryNotFoundException("Could not find any category with ID" + id);
    }
}




public Category save(Category category){
   return categoryRepository.save(category);
}


    public List<Category> listCategoriesUsedInForm(){
    List<Category> categoriesUsedInForm = new ArrayList<>();

    Iterable<Category> categoriesInDM = categoryRepository.findRootCategories(Sort.by("name").ascending());


    for(Category category : categoriesInDM){
        if(category.getParent() == null){
            categoriesUsedInForm.add(Category.getcopyIdAndName(category));
            System.out.println(category.getName());

            Set<Category> children = categorySortedSetSubcategory(category.getChildren());

            for(Category subCategory: children){
                String name = ("--" + subCategory.getName());
                categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
                listSubCategoriesUsedInForm( categoriesUsedInForm,subCategory,1);
            }
        }
    }
        return categoriesUsedInForm;
    }




    private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm ,Category parent, int subLevel){
    int newSublevel = subLevel + 1;

    Set<Category> children = categorySortedSetSubcategory(parent.getChildren());

    for(Category subCategory: children){
        String name = "";
        for(int i = 0; i < newSublevel ; i++){

            name += "--";
        }

        name += subCategory.getName();
        categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));

        System.out.println(subCategory.getName());
        listSubCategoriesUsedInForm( categoriesUsedInForm, subCategory, newSublevel);
        }

    }


    public void delete(Integer id) {
       Category target = categoryRepository.findById(id).get();

        categoryRepository.delete(target);
    }




    public boolean isCategoryUnique( Integer id, String name){
        Category category = categoryRepository.findCategoryByName(name);

        if (category == null) return true;

        boolean isCreatingNewCategory = (id == null);

        if(isCreatingNewCategory){
            if(category != null) return false;
        } else {
            if(category.getId() != id) return false;
        }


        return true;
    }


    private SortedSet<Category> categorySortedSetSubcategory (Set<Category> children){
        SortedSet<Category> sortedChildren = new TreeSet<>(new Comparator<Category>() {
            @Override
            public int compare(Category cat1, Category cat2) {
                return cat1.getName().compareTo(cat2.getName());
            }
        });

        sortedChildren.addAll(children);
        return sortedChildren;

    }












}


