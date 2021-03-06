package com.OnlineStore.OnlineStoreBackEnd.Admin.User.Controllers;



import com.OnlineStore.OnlineStoreBackEnd.Admin.User.Exporters.UserCsvExporter;
import com.OnlineStore.OnlineStoreBackEnd.Admin.User.Exporters.UserPDFExporter;
import com.OnlineStore.OnlineStoreBackEnd.Admin.User.FileUploadUtility;
import com.OnlineStore.OnlineStoreCommon.Exception.UserNotFoundException;
import com.OnlineStore.OnlineStoreBackEnd.Admin.User.UserService;
import com.OnlineStore.OnlineStoreCommon.Entity.Role;
import com.OnlineStore.OnlineStoreCommon.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;




    @GetMapping("/home")
    public String viewHomePage(){
        return "index";
    }

    @GetMapping("/users")
    public String listFirst(Model model){
        listByPage(1, model, "id", "asc", null);
        return "users/users";
    }


    @GetMapping("/users/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum,
                             Model model,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword){

        Page<User> page = userService.listByPage(pageNum,sortField,sortDir,keyword );
        List<User> listUsers = page.getContent();



        long startCount = (pageNum) * UserService.USERS_PER_PAGE + 1  ;
        long endCount = startCount + UserService.USERS_PER_PAGE - 1;
        if(endCount > page.getTotalElements()){endCount = page.getTotalElements();}


        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc"  ;

        model.addAttribute("keyword",keyword);
        model.addAttribute("currentPage",pageNum);
        model.addAttribute("lastPage",page.getTotalPages() );
        model.addAttribute("startCount",startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);

        return "users/users";
    }



    @GetMapping("/users/new")
    public String newUser(Model model){
        List<Role> listRoles = userService.listRoles();

        User user = new User();
        user.setEnabled(true);

        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Create New User");

        return "users/user_form";
    }


    @PostMapping("/users/save")
    public String saveUser(User user,  RedirectAttributes redirectAttributes,
                           @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String myPath =  getMyPath();

        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhoto(fileName);
            User savedUser = userService.save(user);
            userService.save(user);
            String uploadDir = myPath + "user-photos/" + savedUser.getId();

            FileUploadUtility.cleanDirectoryOfOldFile(uploadDir);
            FileUploadUtility.saveFile(uploadDir, fileName, multipartFile);

        }else{
            userService.save(user);
        }


       redirectAttributes.addFlashAttribute("passmessage", "Successful creation of new User");

        String firstPartOfEmail = user.getEmail().split("@")[0];


        return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword=" + firstPartOfEmail;

    }



    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name="id") Integer id,Model model
                        ,RedirectAttributes redirectAttributes){
        try {
            User user = userService.get(id);
            List<Role> listRoles = userService.listRoles();

            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit User (ID: "+ id + "  )");
            model.addAttribute("listRoles", listRoles);

            return "users/user_form";

        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("failmessage", ex.getMessage());

            return "redirect:/users";
        }

    }


    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name="id") Integer id
            ,Model model
            ,RedirectAttributes redirectAttributes){
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("passmessage",
                    "successful deletion of User Id : " + id );


        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("failmessage",
                    ex.getMessage());
        }
        return "redirect:/users";
    }



    @GetMapping("/users/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {

        List<User> listUsers = userService.listAll();
        UserCsvExporter exporter = new UserCsvExporter();
        exporter.export(listUsers,response);

    }


    @GetMapping("/users/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException{

        List<User> listUsers = userService.listAll();
        UserPDFExporter exporter = new UserPDFExporter();
        exporter.export(listUsers,response);

    }




    public String getMyPath(){
        Path currentDirectoryPath = Paths.get("").toAbsolutePath();
        String currentPathString = currentDirectoryPath.toString();
        String result = currentPathString.split("OnlineStoreBackEnd")[0];
        String windowsCompliantPath =  result.replace("\\", "/");

        return windowsCompliantPath + "/OnlineStoreProject/OnlineStoreWebParent/OnlineStoreBackEnd/";

    }



}
