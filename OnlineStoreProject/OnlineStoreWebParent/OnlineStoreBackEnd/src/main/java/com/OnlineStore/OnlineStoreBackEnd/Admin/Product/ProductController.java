package com.OnlineStore.OnlineStoreBackEnd.Admin.Product;


import com.OnlineStore.OnlineStoreBackEnd.Admin.Category.CategoryService;
import com.OnlineStore.OnlineStoreBackEnd.Admin.User.FileUploadUtility;
import com.OnlineStore.OnlineStoreCommon.Entity.Category;
import com.OnlineStore.OnlineStoreCommon.Entity.Product;
import com.OnlineStore.OnlineStoreCommon.Exception.ProductNotFoundException;
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
public class ProductController {

@Autowired
private ProductService productService;

@Autowired
private CategoryService categoryService;

@GetMapping("/products")
public String listAllProductsHome(Model model){

    List<Product> products = productService.listAll();

    model.addAttribute("products", products);

    return "products/products";
}

@GetMapping("products/new")
    public String newProduct(Model model){

    List<Category> categoryList = categoryService.noHierarchyListAll();

    Product product = new Product();
    product.setEnabled(true);
    product.setInStock(true);

    model.addAttribute("product",product);
    model.addAttribute("categoryList", categoryList);
    model.addAttribute("pageTitle", " Create new Product");


    return "products/product_form";

}

@PostMapping("/products/save")
public String saveProduct(Product product,Model model, RedirectAttributes redirectAttributes,
                          @RequestParam("fileImage") MultipartFile multipartFile,
                          @RequestParam("extraImage") MultipartFile extraFile) throws IOException {

    String myPath =  getMyPath();

    if(!multipartFile.isEmpty()){
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        product.setMainImage(fileName);

        Product savedProduct = productService.save(product);


        String uploadDirMain = myPath + "product-images/" + savedProduct.getId();

        String uploadDirExtra = uploadDirMain + "/extraImage";

        FileUploadUtility.cleanDirectoryOfOldFile(uploadDirMain);
        FileUploadUtility.saveFile(uploadDirMain, fileName, multipartFile);
    } else{
        productService.save(product);
    }


    if(!extraFile.isEmpty()){
        String extraFileName = StringUtils.cleanPath(extraFile.getOriginalFilename());
        product.addExtraImage(extraFileName);

        String uploadDirExtra = myPath + "product-images/" + product.getId() + "/extraImage";

        FileUploadUtility.cleanDirectoryOfOldFile(uploadDirExtra);
        FileUploadUtility.saveFile(uploadDirExtra, extraFileName, extraFile);

        productService.save(product);
    }

    model.addAttribute("product",product);
    redirectAttributes.addFlashAttribute("passmessage", "Successful save of " + product.getName());

    return "redirect:/products" ;

}



    @GetMapping("/products/edit/{id}")
    public String editCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {

        List<Category> categoryList = categoryService.noHierarchyListAll();

        try{
            Product product = productService.get(id);


            model.addAttribute("product",product);
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("pageTitle", "Edit Product (Id :" + id + " )");

            return"products/product_update";
        }catch (ProductNotFoundException ex){
            redirectAttributes.addFlashAttribute("failmessage", ex.getMessage());
            return "redirect:/products";
        }
    }

@GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable(name="id") Integer id, Model model, RedirectAttributes redirectAttributes){

    try{
        productService.delete(id);
        String directory = "../product-images/" + id;
        FileUploadUtility.cleanDirectory(directory);
        redirectAttributes.addFlashAttribute("passmessage", "product ID " + id + " was deleted");
    }catch (ProductNotFoundException ex) {
        redirectAttributes.addFlashAttribute("failmessage", ex.getMessage());
    }
    return "redirect:/products";


}



    @PostMapping("/products/update")
    public String updateProduct(Product product,Model model, RedirectAttributes redirectAttributes,
                              @RequestParam("fileImage2") MultipartFile multipartFile,
                              @RequestParam("extraImage") MultipartFile extraFile) throws IOException {

        String myPath =  getMyPath();

        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            product.setMainImage(fileName);

            Product savedProduct = productService.save(product);


            String uploadDirMain = myPath + "product-images/" + savedProduct.getId();


            FileUploadUtility.cleanDirectoryOfOldFile(uploadDirMain);
            FileUploadUtility.saveFile(uploadDirMain, fileName, multipartFile);
        } else{

            productService.save(product);
        }


        if(!extraFile.isEmpty()){
            String extraFileName = StringUtils.cleanPath(extraFile.getOriginalFilename());
            product.addExtraImage(extraFileName);

            String uploadDirExtra = myPath + "product-images/" + product.getId() + "/extraImage";

            FileUploadUtility.cleanDirectoryOfOldFile(uploadDirExtra);
            FileUploadUtility.saveFile(uploadDirExtra, extraFileName, extraFile);

            productService.save(product);
        }

        model.addAttribute("product",product);
        redirectAttributes.addFlashAttribute("passmessage", "Successful save of " + product.getName());

        return "redirect:/products" ;

    }

public String getMyPath(){
    Path currentDirectoryPath = Paths.get("").toAbsolutePath();
    String currentPathString = currentDirectoryPath.toString();
    String result = currentPathString.split("OnlineStoreBackEnd")[0];
    String windowsCompliantPath =  result.replace("\\", "/");

    return windowsCompliantPath + "/OnlineStoreProject/OnlineStoreWebParent/";

}


}
