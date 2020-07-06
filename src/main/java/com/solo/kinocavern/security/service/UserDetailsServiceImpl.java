package com.solo.kinocavern.security.service;

import com.solo.kinocavern.dao.UserDAO;
import com.solo.kinocavern.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserDAO userDAO;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username){
        User user = userDAO.findByUsername(username);

        return UserDetailsImpl.build(user);
    }

}
