/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lantos.manager;

import com.lantos.base64password.CreateAndValidatePassword;
import com.lantos.model.Role;
import com.lantos.model.User;
import com.lantos.serviceInterface.UserService;
import java.util.Map;

/**
 *
 * @author PETI
 */
public class UserManager {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }
    
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public User loginUser(Map<String, String> reqPar) {
        String email = reqPar.get("email");
        String password = reqPar.get("password");

        if (email != null && password != null) {
            User user = null;
            try {
               user = this.userService.getUserByEmail(email);
            } catch (Exception e) {
               // throw new IllegalStateException("Nincs db");
                e.printStackTrace();
            }

            if (user != null
                    && CreateAndValidatePassword.validatePassword(password,
                            CreateAndValidatePassword.base64Decode(user.getPassword()))) {
                return user;
            }else {
                return null;
            }

        }
        return null;
    }

    public boolean addedNewUser(Map<String, String> reqPar) {
        String username = reqPar.get("username");
        String password = reqPar.get("password");
        String tmpPassword = CreateAndValidatePassword.base64Encode(password);
        String fullName = reqPar.get("fullName");
        String age = reqPar.get("age");
        String email = reqPar.get("email");
        
        User user = new User();
        user.setRole(Role.USER);
        user.setUsername(username);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setPassword(tmpPassword);
        user.setAge(Integer.parseInt(age));
        
        this.userService.saveUser(user);
        
        return true;
    }
    
    public boolean modifyUser(Map<String, String> reqPar) {
        String username = reqPar.get("userName");
        String password = reqPar.get("password");
        String tmpPassword = CreateAndValidatePassword.base64Encode(password);
        String fullName = reqPar.get("fullName");
        String age = reqPar.get("age");
        String email = reqPar.get("email");
        String role = reqPar.get("role");
        
        User user = userService.getUserByEmail(email);
        user.setRole(Role.USER);
        user.setUsername(username);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setPassword(tmpPassword);
        user.setAge(Integer.parseInt(age));
        
        return true;
    }
    
    public void deleteUserById(long id){
        this.userService.removeUserById(id);
    }
    
    public void changeRole(long id){
        User user = this.userService.getUserById(id);
        if(user.getRole().equals(Role.USER)){
            user.setRole(Role.ADMIN);
        }else{
            user.setRole(Role.USER);
        }
        this.userService.updateUser(user);
    }
    
    public void changePassword(Map<String, String> reqPar){
        String idstr = reqPar.get("hiddenUserID");
        Long id = null;
        if(idstr!=null){
            id=  Long.parseLong(idstr);
        }
        User user = null;
        if(id!=null){
            user= this.userService.getUserById(id);
        }
        String password = CreateAndValidatePassword.base64Encode(reqPar.get("password2"));
        user.setPassword(password);
        this.userService.updateUser(user);
    }
    
    public boolean emailAlreadyInUser(String email) {
        return this.userService.getUserByEmail(email) != null;
    }
    
    

}
