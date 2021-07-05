package com.OnlineStore.OnlineStoreBackEnd;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MainController {


    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }

    @GetMapping("/index")
    public String viewHomePage2(){
        return "index";
    }

    @GetMapping("/login")
    public String viewLoginPage(){
        return"login";
    }


}
