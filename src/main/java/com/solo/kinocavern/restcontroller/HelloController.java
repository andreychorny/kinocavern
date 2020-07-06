package com.solo.kinocavern.restcontroller;

import com.solo.kinocavern.dao.UserDAO;
import com.solo.kinocavern.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    public UserDAO userDAO;
    @GetMapping("/")
    public String abc(){
        return "WORKING!";
    }

    @GetMapping("/user/{email}")
    public Boolean getUser(@PathVariable String email){
        Boolean boo = userDAO.emailExists(email);
        return boo;
    }
    @GetMapping("/users/{username}")
    public User getUserNickname(@PathVariable String username){
        return userDAO.findByUsername(username);
    }

}
