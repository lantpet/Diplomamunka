package com.lantos.controller;

import com.lantos.manager.UserManager;
import com.lantos.model.Book;
import com.lantos.model.User;
import com.lantos.serviceInterface.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class LoginController {

        private UserManager usermanager = new UserManager();
        
        @Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService as) {
		this.usermanager.setUserService(as);
	}
    
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginForm(HttpSession session,Locale locale, Model model, @RequestParam Map<String, String> reqPar) {	
		User user = null;
		try{
			user = this.usermanager.loginUser(reqPar);
		}
		catch(IllegalStateException e){
			model.addAttribute("dbNotFound", "dbNotFound");
                        e.printStackTrace();
			return "login";
		}
		
		if(user != null){
			session.setAttribute("userEmail", user.getEmail());
                        session.setAttribute("role", user.getRole());
                	return "redirect:/userdetails";
		}
		else {
			model.addAttribute("notSuccessLogin", "notSuccessLogin");
			return "login";
		}
	}
        
        @RequestMapping(value = "/login")
        public String loginFor(HttpSession session,Locale locale, Model model, @RequestParam Map<String, String> reqPar) {
            return "login";
        }	

}
