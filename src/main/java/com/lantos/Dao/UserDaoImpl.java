/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lantos.Dao;

import com.lantos.daoInterface.UserDao;
import com.lantos.model.Role;
import com.lantos.model.User;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author PETI
 */
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User getUserByEmail(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where email = :name");
        query.setParameter("name", name);
        List<User> userList = query.list();
        return userList.size() < 1 ? null : userList.get(0);
    }

    @Override
    public void removeUserById(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        User e = (User) session.get(User.class, new Long(id));
        if (e != null) {
            session.delete(e);
        }
    }

    @Override
    public void updateUser(User e) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(e);
    }

    @Override
    public void saveUser(User u) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(u);
    }

    @Override
    public List<User> getAllUser() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> userList = session.createQuery("from User").list();
        return userList;
    }

    @Override
    public Role getRoleByName(String r) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Role where email = :name");
        query.setParameter("name", r);
        List<Role> roleList = query.list();
        return roleList.size() < 1 ? null : roleList.get(0);
    }

    @Override
    public User getUserById(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User where id = :id");
        query.setParameter("id", id);
        List<User> userList = query.list();
        return userList.size() < 1 ? null : userList.get(0);
    }

}
