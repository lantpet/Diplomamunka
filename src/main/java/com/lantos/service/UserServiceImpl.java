/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lantos.service;

import com.lantos.daoInterface.UserDao;
import com.lantos.model.Role;
import com.lantos.model.User;
import com.lantos.serviceInterface.UserService;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        return this.userDao.getUserByEmail(email);
    }

    @Override
    @Transactional
    public void removeUserById(Long id) {
        this.userDao.removeUserById(id);
    }

    @Override
    @Transactional
    public void saveUser(User b) {
        this.userDao.saveUser(b);
    }

    @Override
    @Transactional
    public List<User> getAllUser() {
        return this.userDao.getAllUser();
    }

    @Override
    @Transactional
    public void updateUser(User e) {
        this.userDao.updateUser(e);
    }

    @Override
    @Transactional
    public Role getRoleByName(String role) {
        return this.userDao.getRoleByName(role);
    }

    @Override
    @Transactional
    public User getUserById(long id) {
        return this.userDao.getUserById(id);
    }
}
