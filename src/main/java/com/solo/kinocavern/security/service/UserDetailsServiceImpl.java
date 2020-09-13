package com.solo.kinocavern.security.service;

import com.solo.kinocavern.dao.UserDAO;
import com.solo.kinocavern.entity.User;
import com.solo.kinocavern.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userDAO.findByUsername(username);

        return UserDetailsImpl.build(user);
    }

}
