package com.lantos.controller;

import com.lantos.manager.UserManager;
import com.lantos.model.Role;
import com.lantos.model.User;
import com.lantos.serviceInterface.UserService;
import com.lantos.sessionmanager.SessionManager;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController{

    @Autowired
    private ServletContext context;
    
    UserManager usermanager = new UserManager();
    
    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setBookService(UserService es) {
        this.usermanager.setUserService(es);
    }
    
    @RequestMapping(value = "/users")
    public String UserTable(HttpSession session, Model model) {
        User loginuser = this.usermanager.getUserService().getUserByEmail((String) session.getAttribute("userEmail"));
        if (SessionManager.redirectToHomePage(session)) {
            return "redirect:/";
        }
        
        if(loginuser.getRole()!= Role.ADMIN){
            return "redirect:/books";
        }
        
        List<User> allUser = this.usermanager.getUserService().getAllUser();
        List<User> users = new ArrayList<User>();
        for(User user: allUser){
            if(!loginuser.getEmail().equals(user.getEmail())){
                users.add(user);
            }
        }
        model.addAttribute("userList", users);
        model.addAttribute("roles",session.getAttribute("role"));
        return "users";
    }
    
    @RequestMapping(value = "/changeRole.{userId}")
    public String changeRole(@PathVariable("userId") long id) {
        this.usermanager.changeRole(id);
        return "redirect:/users";
    }
    
    @RequestMapping(value="/removeUser.{bookId}")
    public String removeUsers(HttpSession session,@PathVariable("bookId") long id,RedirectAttributes redirAttr){
        User user = usermanager.getUserService().getUserByEmail((String) session.getAttribute("userEmail"));
        if(user.getId()!=id){
        this.usermanager.deleteUserById(id);
        }else{
            redirAttr.addFlashAttribute("succesPayed", "Az aktuálissan bejelentkezett felhasználó nem törölhető");
        }
        return "redirect:/users";
    }
    
   
    
    
    
}
