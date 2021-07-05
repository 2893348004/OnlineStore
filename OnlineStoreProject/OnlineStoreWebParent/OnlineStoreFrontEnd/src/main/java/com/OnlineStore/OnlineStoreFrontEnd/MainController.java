package com.OnlineStore.OnlineStoreFrontEnd;


import com.OnlineStore.OnlineStoreCommon.Entity.Category;
import com.OnlineStore.OnlineStoreFrontEnd.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String getHomePage(Model model){

        List<Category> categoryList = categoryService.listWithNoChildrenCategories();

        model.addAttribute("categoryList",categoryList);
        return "index";
    }

    @GetMapping("/home")
    public String HomePage(Model model){

        List<Category> categoryList = categoryService.listWithNoChildrenCategories();

        model.addAttribute("categoryList",categoryList);
        return "index";
    }

    @GetMapping("/login")
    public String viewLoginPage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }

        return "index";
    }


}
