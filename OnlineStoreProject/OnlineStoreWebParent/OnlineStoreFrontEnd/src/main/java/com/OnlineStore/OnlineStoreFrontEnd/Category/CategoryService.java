package com.OnlineStore.OnlineStoreFrontEnd.Category;

import com.OnlineStore.OnlineStoreCommon.Entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

@Autowired
private CategoryRepository categoryRepository;

public List<Category> listWithNoChildrenCategories(){
    List<Category> listNoChildrenCategories = new ArrayList<>();

    List<Category> listEnabledCategories = categoryRepository.findAllEnabled();

    listEnabledCategories.forEach(category -> {
       Set<Category> children = category.getChildren();
        if(children == null || children.size() == 0){
            listNoChildrenCategories.add(category);
        }

    });
    return listNoChildrenCategories;
}



public Category getCategory(String alias){
    return categoryRepository.findByAliasEnabled(alias);
}

public List<Category> getCategoryParents(Category child){
    List<Category> listParents = new ArrayList<>();

    Category parent = child.getParent();

    while(parent != null){
        listParents.add(0, parent);
        parent = parent.getParent();

    }
    listParents.add(child);

    return listParents;

}



}
