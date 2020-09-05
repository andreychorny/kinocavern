package com.solo.kinocavern.serviceimpl;

import com.solo.kinocavern.dao.UserDAO;
import com.solo.kinocavern.entity.*;
import com.solo.kinocavern.security.util.JwtUtils;
import com.solo.kinocavern.service.CommentService;
import com.solo.kinocavern.service.MovieService;
import com.solo.kinocavern.service.NotificationService;
import com.solo.kinocavern.service.UserService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificationService notificationService;

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userDAO.findById(id);
    }

    @Override
    public void addRating(HttpServletRequest request, Long movieId, int rate){
        User currentUser = this.loadCurrentUser(request);
        Movie movie = movieService.findById(movieId);
        deleteRatingIfExist(movieId, currentUser);
        deleteWishlistIfExist(movieId, currentUser);
        Rating rating = new Rating(rate, currentUser, movie);
        currentUser.getRatings().add(rating);
        movieService.updateAverageRating(movieId);
        userDAO.save(currentUser);
        notificationService.sendRateNotificationsToSubscribers(movie, currentUser, rate);
    }

    @Override
    public Map<String,String> addChatMessage(HttpServletRequest request,
                               Long userToId,
                               String message){
        User currentUser = this.loadCurrentUser(request);
        User userTo = this.findById(userToId);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        response.put("fromId", currentUser.getId().toString());
        response.put("toId", userToId.toString());
        this.simpMessagingTemplate.convertAndSend("/socket-publisher/"+currentUser.getId(), response);
        this.simpMessagingTemplate.convertAndSend("/socket-publisher/"+userToId, response);
        System.out.println(userToId);
        Chat chatMessage = new Chat(currentUser, userTo, message);
        currentUser.addMessage(chatMessage);
        userDAO.save(currentUser);
        notificationService.sendChatNotification(currentUser, userTo);
        return response;
    }

    @Override
    public void addToWishlist(HttpServletRequest request, Long movieId){
        User currentUser = this.loadCurrentUser(request);
        deleteRatingIfExist(movieId, currentUser);
        //if we have movie in wishlist, we delete it. If not - add it
        if(!deleteWishlistIfExist(movieId, currentUser)){
            Movie movie = movieService.findById(movieId);
            currentUser.addMovieToWishlist(movie);
            userDAO.save(currentUser);
            currentUser.getWishlist().size();
        }
    }

    @Override
    public void addComment(HttpServletRequest request, Long movieId, String content, Long parentId) {
        User currentUser = this.loadCurrentUser(request);
        Movie movie = movieService.findById(movieId);
        Comment comment = new Comment(content, parentId, currentUser, movie);
        commentService.save(comment);
    }

    @Override
    public void subscribeTo(HttpServletRequest request, Long userId) {
        User currentUser = this.loadCurrentUser(request);
        User userSubscribeTo = this.findById(userId);
        if(!currentUser.equals(userSubscribeTo)){
            if(!currentUser.getSubscriptions().contains(userSubscribeTo)){
                currentUser.addUserToSubscription(userSubscribeTo);
            }else{
                currentUser.getSubscriptions().remove(userSubscribeTo);
            }
        }
        userDAO.save(currentUser);
    }

    @Override
    public Boolean checkIfUserHasNewNotifications(HttpServletRequest request){
        Long currentUserId = this.loadCurrentUserId(request);
        return notificationService.checkIfUserIdHasNewNotifications(currentUserId);
    }

    @Override
    public User loadCurrentUser(HttpServletRequest request) {
        String token = parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(token);
        return userDAO.findByUsername(username);
    }

    @Override
    public Long loadCurrentUserId(HttpServletRequest request) {
        String token = parseJwt(request);
        Long id = jwtUtils.getIdFromJwtToken(token);
        return id;
    }

    @Override
    public List<Notification> loadNotificationsOfUser(HttpServletRequest request){
        User user = loadCurrentUser(request);
        List<Notification> notifications = user.getNotifications();
        for(Notification notification: notifications){
            notification.setUnseen(false);
        }
        System.out.println(notifications.get(0).isUnseen());
        deleteOldNotifications(user, notifications);
        userDAO.save(user);
        return notifications;
    }

    @Override
    public void deleteOldNotifications(User user, List<Notification> notifications){
        List<Notification> newestNotifications;
        if(notifications.size()>=30){
            System.out.println(notifications.get(0).isUnseen());
            newestNotifications = notifications.subList(notifications.size()-30,
                    notifications.size());
            user.setNotifications(newestNotifications);
        }
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }

    public void deleteRatingIfExist(Long movieId, User user) {
        Set<Rating> allRatings = user.getRatings();
        for (Rating rating : allRatings) {
            if (rating.getMovieId() == movieId) {
                user.getRatings().remove(rating);
                //update user's ratings
                userDAO.save(user);
                break;
            }
        }
    }

    public boolean deleteWishlistIfExist(Long movieId, User user) {
        Set<Movie> wishlistedMovies = user.getWishlist();
        for (Movie movie : wishlistedMovies) {
            if (movie.getId() == movieId) {
                user.getWishlist().remove(movie);
                //update user's ratings
                userDAO.save(user);
                return true;
            }
        }
        return false;
    }
}
