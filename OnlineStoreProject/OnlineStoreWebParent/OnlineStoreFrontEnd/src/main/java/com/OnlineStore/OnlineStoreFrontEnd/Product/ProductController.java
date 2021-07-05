package com.OnlineStore.OnlineStoreFrontEnd.Product;

import com.OnlineStore.OnlineStoreCommon.Entity.Category;
import com.OnlineStore.OnlineStoreCommon.Entity.Product;
import com.OnlineStore.OnlineStoreCommon.Exception.ProductNotFoundException;
import com.OnlineStore.OnlineStoreFrontEnd.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ProductController {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/c/{category_alias}")
    public String viewCategoryFirstPage(@PathVariable("category_alias") String alias,
                                     Model model){
        return viewCategorybyPage(alias,model,1);
    }


    @GetMapping("/c/{category_alias}/page/{pageNum}")
    public String viewCategorybyPage(@PathVariable("category_alias") String alias,
                               Model model,
                               @PathVariable("pageNum") int pageNum){
        Category category = categoryService.getCategory(alias);
        if(category == null){
            return "error/404";
        }

        Set<Category> subCategories = category.getChildren();
        List<Product> subCategoryProducts = new ArrayList<>();

        for(Category cat: subCategories ){
            Page<Product> subPageProducts =  productService.listCategory(pageNum, cat.getId());
            List<Product> subListProducts = subPageProducts.getContent();

            for( Product prod: subListProducts){
                subCategoryProducts.add(prod);
            }
        }

        List<Category> listParents = categoryService.getCategoryParents(category);
        Page<Product> pageProducts =  productService.listCategory(pageNum, category.getId());
        List<Product> listProducts = pageProducts.getContent();






        long startCount = (pageNum) * productService.PRODUCTS_PER_PAGE + 1  ;
        long endCount = startCount + productService.PRODUCTS_PER_PAGE - 1;
        if(endCount > pageProducts.getTotalElements()){endCount = pageProducts.getTotalElements();}


        model.addAttribute("currentPage",pageNum);
        model.addAttribute("totalPages",pageProducts.getTotalPages() );
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", pageProducts.getTotalElements());
        model.addAttribute("category", category);
        model.addAttribute("pageTitle", category.getName());
        model.addAttribute("listParents", listParents);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("subCategoryProducts", subCategoryProducts);


        return "products/products_by_category";
    }

    @GetMapping("/p/{product_alias}")
    public String viewProductDetail(@PathVariable("product_alias") String alias,
                                    Model model){

        try {
           Product product =  productService.getProduct(alias);
            List<Category> listParents = categoryService.getCategoryParents(product.getCategory());

            model.addAttribute("listParents", listParents);
            model.addAttribute("product", product);
            model.addAttribute("pageTitle", product.getName());

            return "products/products_detail";
        } catch (ProductNotFoundException e) {
            return "error/404";
        }

    }


}
