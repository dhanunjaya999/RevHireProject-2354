package com.revhire.dao;

import com.revhire.model.Employer;
import com.revhire.model.User;


public interface UserDao {

    void save(User user);

    User findByEmail(String email);
    
    void updateEmployerProfile(Employer employer);

}

