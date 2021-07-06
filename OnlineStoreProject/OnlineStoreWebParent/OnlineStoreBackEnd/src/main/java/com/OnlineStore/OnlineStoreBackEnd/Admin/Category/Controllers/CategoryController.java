package com.OnlineStore.OnlineStoreBackEnd.Admin.Category.Controllers;

import com.OnlineStore.OnlineStoreCommon.Exception.CategoryNotFoundException;
import com.OnlineStore.OnlineStoreBackEnd.Admin.Category.CategoryService;
import com.OnlineStore.OnlineStoreBackEnd.Admin.User.FileUploadUtility;
import com.OnlineStore.OnlineStoreCommon.Entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class CategoryController {

@Autowired
    private CategoryService categoryService;

@GetMapping("/categories")
    public String listAll(Model model){
    List<Category> listCategories = categoryService.listAll();
    model.addAttribute("listCategories", listCategories);

    return "categories/categories";
}


    @GetMapping("/categories/new")
    public String newCategory(Model model){

    List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        model.addAttribute("category", new Category());
        model.addAttribute("pageTitle", "Create / Edit Category");
        model.addAttribute("listCategories", listCategories);

        return "categories/categories_form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(Category category, @RequestParam("file") MultipartFile multipartFile,
                               RedirectAttributes redirectAttributes) throws IOException {

        String myPath =  getMyPath();

    if(!multipartFile.isEmpty()){
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        category.setImage(fileName);
        Category savedCategory = categoryService.save(category);

        String uploadDir = myPath  + "category-images/" + savedCategory.getId();

        FileUploadUtility.cleanDirectoryOfOldFile(uploadDir);
        FileUploadUtility.saveFile(uploadDir, fileName, multipartFile);
    } else{
        categoryService.save(category);
    }

    redirectAttributes.addFlashAttribute("passmessage", "Successful saving of Category :" + category.getName());

    return "redirect:/categories";
    }




    @GetMapping("/categories/edit/{id}")
    public String editCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {

    try{
        Category category = categoryService.get(id);
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        model.addAttribute("category",category);
        model.addAttribute("listCategories",listCategories);
        model.addAttribute("pageTitle", "Edit Category (Id :" + id + " )");

        return"categories/categories_form";
    }catch (CategoryNotFoundException ex){
        redirectAttributes.addFlashAttribute("message", ex.getMessage());
        return "redirect:/categories";
        }
    }


    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable(name="id") Integer id
            ,Model model
            ,RedirectAttributes redirectAttributes){

            categoryService.delete(id);
            redirectAttributes.addFlashAttribute("passmessage",
                    "successful deletion of Category Id : " + id );


        return "redirect:/categories";
    }


    public String getMyPath(){
        Path currentDirectoryPath = Paths.get("").toAbsolutePath();
        String currentPathString = currentDirectoryPath.toString();
        String result = currentPathString.split("OnlineStoreBackEnd")[0];
        String windowsCompliantPath =  result.replace("\\", "/");

        return windowsCompliantPath + "/OnlineStoreProject/OnlineStoreWebParent/";

    }


}
