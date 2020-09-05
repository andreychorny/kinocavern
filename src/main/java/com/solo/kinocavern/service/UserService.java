package com.solo.kinocavern.service;

import com.solo.kinocavern.entity.Movie;
import com.solo.kinocavern.entity.Notification;
import com.solo.kinocavern.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {

    public List<User> findAll();

    public User findByUsername(String username);

    public User findById(Long id);

    public User loadCurrentUser(HttpServletRequest request);

    public void deleteRatingIfExist(Long movieId, User user);

    public void addRating(HttpServletRequest request, Long movieId, int rate);

    public void addToWishlist(HttpServletRequest request, Long movieId);

    public void addComment(HttpServletRequest request, Long movieId, String content, Long parentId);

    public void subscribeTo(HttpServletRequest request, Long userId);

    public Map<String, String> addChatMessage(HttpServletRequest request, Long userToId, String message);

    public Boolean checkIfUserHasNewNotifications(HttpServletRequest request);

    public Long loadCurrentUserId(HttpServletRequest request);

    public List<Notification> loadNotificationsOfUser(HttpServletRequest request);

    public void deleteOldNotifications(User user, List<Notification> notifications);
}
