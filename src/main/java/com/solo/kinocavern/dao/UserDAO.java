package com.solo.kinocavern.dao;

import com.solo.kinocavern.entity.User;

import java.util.List;

public interface UserDAO {

    List<User> findAll();

    public void test();

    User findById(Long id);

    Boolean usernameExists(String username);

    Boolean emailExists(String email);

    User findByUsername(String username);

    void save(User user);

    void deleteById(Long id);

}
