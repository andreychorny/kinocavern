package com.solo.kinocavern.restcontroller;

import com.solo.kinocavern.entity.Notification;
import com.solo.kinocavern.entity.Rating;
import com.solo.kinocavern.entity.User;
import com.solo.kinocavern.dto.request.RatingFormWrapper;
import com.solo.kinocavern.dto.response.UserProfile;
import com.solo.kinocavern.service.CommentService;
import com.solo.kinocavern.service.NotificationService;
import com.solo.kinocavern.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserRestController {

    @Autowired
    public UserService userService;

    @Autowired
    public CommentService commentService;

    @Autowired
    public NotificationService notificationService;

    @GetMapping("/users/{userId}")
    public UserProfile getUserProfile(@PathVariable Long userId) {

        User user = userService.findById(userId);
        UserProfile userProfile = new UserProfile(user);
        return userProfile;
    }

    @PostMapping("/rates")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Rating addRating(HttpServletRequest request,
                            @RequestBody RatingFormWrapper ratingForm)  {
        Long movieId = ratingForm.getMovieId();
        int rate = ratingForm.getRate();
        userService.addRating(request, movieId, rate);
        return null;
    }

    @PostMapping("/wishlists")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Rating addToWishlist(HttpServletRequest request,
                            @RequestBody Long movieId)  {
        userService.addToWishlist(request, movieId);
        return null;
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable Long userId)  {
        userService.deleteById(userId);
    }

    @PostMapping("/users/subscribeTo/{userId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public void subscribeTo(HttpServletRequest request, @PathVariable Long userId) {
        User user = userService.findById(userId);
        userService.subscribeTo(request, userId);
    }

    @GetMapping("/users/newNotifications")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Boolean getIfUserHasNewNotification(HttpServletRequest request) {
        Boolean result = userService.checkIfUserHasNewNotifications(request);
        return result;
    }

    @GetMapping("/notifications")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public List<Notification> getNotifications(HttpServletRequest request) {
        List<Notification> notifications = userService.loadNotificationsOfUser(request);
        Collections.reverse(notifications);
        return notifications;
    }

}
