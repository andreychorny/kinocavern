package com.solo.kinocavern.service;

import com.solo.kinocavern.entity.Genre;
import com.solo.kinocavern.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    public List<User> findAll();

    public User findByUsername(String username);

    public User findById(Long id);

    public User loadCurrentUser(HttpServletRequest request);

    public void deleteRatingIfExist(Long movieId, User user);

    public void addRating(HttpServletRequest request, Long movieId, int rate);

    public void addToWishlist(HttpServletRequest request, Long movieId);

    public void addComment(HttpServletRequest request, Long movieId, String content, Long parentId);
}
