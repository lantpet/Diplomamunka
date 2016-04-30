/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lantos.controller;

import com.lantos.manager.UserManager;
import com.lantos.serviceInterface.UserService;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegistrationController {

    UserManager userManager = new UserManager();

    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService es) {
        this.userManager.setUserService(es);
    }

    @RequestMapping(value = "/registration")
    public String registration(HttpSession session, Locale locale, Model model, @RequestParam Map<String, String> reqPar) {
        return "registration";
    }

    @RequestMapping(value = "/added_user")
    public String addedNewUser(HttpSession session, Locale locale, Model model, @RequestParam Map<String, String> reqPar) {

        if (this.userManager.addedNewUser(reqPar)) {
            return "redirect:/login";
        }

        return null;
    }
    
    @RequestMapping(value = "/email_is_in_use.{email}", method = RequestMethod.GET)
    public @ResponseBody
    String EmailAlreadyInUse(@PathVariable("email") String email) {
        if (this.userManager.emailAlreadyInUser(email)) {
            return "true";
        } else {
            return "false";
        }
    }

}
