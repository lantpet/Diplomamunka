/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lantos.serviceInterface;

import com.lantos.model.Role;
import com.lantos.model.User;
import java.util.List;

/**
 *
 * @author PETI
 */
public interface UserService {
    
    public User getUserByEmail(String email);
    public void removeUserById(Long id);
    public void saveUser(User b);
    public List<User> getAllUser();
    public void updateUser(User e);
    public Role getRoleByName(String role);
    public User getUserById(long id);
}
