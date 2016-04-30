package com.lantos.daoInterface;

import com.lantos.model.Role;
import com.lantos.model.User;
import java.util.List;

public interface UserDao {

    public User getUserByEmail(String name);

    public void removeUserById(long id);

    public void updateUser(User e);

    public void saveUser(User u);

    public List<User> getAllUser();
    
    public Role getRoleByName(String r);
    
    public User getUserById(long id);
}
