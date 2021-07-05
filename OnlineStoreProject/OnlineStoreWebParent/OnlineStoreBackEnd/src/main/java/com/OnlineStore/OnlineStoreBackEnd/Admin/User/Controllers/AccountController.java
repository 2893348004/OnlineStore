package com.OnlineStore.OnlineStoreBackEnd.Admin.User.Controllers;


import com.OnlineStore.OnlineStoreBackEnd.Admin.Security.OnlineStoreUserDetails;
import com.OnlineStore.OnlineStoreBackEnd.Admin.User.FileUploadUtility;
import com.OnlineStore.OnlineStoreBackEnd.Admin.User.UserService;
import com.OnlineStore.OnlineStoreCommon.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class AccountController {

@Autowired
private UserService userService;

@GetMapping("/account")
    public String viewDetails(@AuthenticationPrincipal OnlineStoreUserDetails loggedUser,
                              Model model){

    String email = loggedUser.getUsername();
    User user = userService.getByEmail(email);
    model.addAttribute("user", user);

    return "users/account_form";
}

    @PostMapping("/account/update")
    public String saveUserDetails(User user , RedirectAttributes redirectAttributes,
                           @RequestParam("image") MultipartFile multipartFile) throws IOException {

        String myPath =  getMyPath();

        if(!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhoto(fileName);
            User savedUser = userService.updateUserAccount(user);
            userService.updateUserAccount(user);
            String uploadDir = myPath + "user-photos/" + savedUser.getId();

            FileUploadUtility.cleanDirectoryOfOldFile(uploadDir);
            FileUploadUtility.saveFile(uploadDir, fileName, multipartFile);

        }else{


            userService.updateUserAccount(user);
        }


        redirectAttributes.addFlashAttribute("passmessage", "Successful updating og your account details ");

        String firstPartOfEmail = user.getEmail().split("@")[0];


        return "redirect:/account";

    }


    public String getMyPath(){
        Path currentDirectoryPath = Paths.get("").toAbsolutePath();
        String currentPathString = currentDirectoryPath.toString();
        String result = currentPathString.split("OnlineStoreBackEnd")[0];
        String windowsCompliantPath =  result.replace("\\", "/");

        return windowsCompliantPath + "/OnlineStoreProject/OnlineStoreWebParent/OnlineStoreBackEnd/";

    }


}
