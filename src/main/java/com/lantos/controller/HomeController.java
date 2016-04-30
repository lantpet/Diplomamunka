/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lantos.controller;

import com.lantos.manager.MailManager;
import com.lantos.manager.UserManager;
import com.lantos.model.User;
import com.lantos.serviceInterface.UserService;
import com.lantos.sessionmanager.SessionManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ServletContext context;

    private MailManager mailManager = new MailManager();
      UserManager usermanager = new UserManager();
    
    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setBookService(UserService es) {
        this.usermanager.setUserService(es);
        this.mailManager.setUserservice(es);
    }

    @Autowired
    public void setMailService(JavaMailSender mailSender) {
        this.mailManager.setMailSender(mailSender);
    }    

    @RequestMapping(value = "/")
    public String FirstPage(HttpSession session) {
        return "login";
    }
    
    @RequestMapping(value = "/userdetails")
    public String userdetails(HttpSession session, Model model) {
        if (SessionManager.redirectToHomePage(session)) {
            return "redirect:/";
        }
        
        List<User> users = new ArrayList<User>();
        User user = this.usermanager.getUserService().getUserByEmail((String) session.getAttribute("userEmail"));
        users.add(user);
        model.addAttribute("loginuser", user);
        model.addAttribute("userid", users);
        model.addAttribute("roles", session.getAttribute("role"));
        return "userdetails";
    }
    

    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.setAttribute("userEmail", "");
        session.setAttribute("role", "");
        session.setAttribute("productList", "");
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/recover_password_token", method = RequestMethod.POST)
    public String sendPasswordToken(@RequestParam("email") String email) {
        
        mailManager.sendRecoverPassword(email);
        return "redirect:/";
    }
    
    @RequestMapping(value="/changePassword" , method = RequestMethod.POST) 
    public String changePassworD(@RequestParam Map<String, String> reqPar){
        this.usermanager.changePassword(reqPar);
        return "redirect:/userdetails";
    }
    
}
