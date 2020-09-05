package com.solo.kinocavern.restcontroller;

import com.solo.kinocavern.dao.MovieDAO;
import com.solo.kinocavern.dao.UserDAO;
import com.solo.kinocavern.entity.Comment;
import com.solo.kinocavern.entity.Notification;
import com.solo.kinocavern.entity.Rating;
import com.solo.kinocavern.entity.User;
import com.solo.kinocavern.payload.request.CommentFormWrapper;
import com.solo.kinocavern.payload.request.RatingFormWrapper;
import com.solo.kinocavern.payload.response.UserProfile;
import com.solo.kinocavern.service.NotificationService;
import com.solo.kinocavern.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserRestController {

    @Autowired
    public UserService userService;

    @Autowired
    public NotificationService notificationService;

    @GetMapping("/users/{userId}")
    public UserProfile getUserProfile(@PathVariable Long userId) {

        User user = userService.findById(userId);
        UserProfile userProfile = new UserProfile(user);
        return userProfile;
    }

    @PostMapping("/users/rate")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Rating addRating(HttpServletRequest request,
                            @RequestBody RatingFormWrapper ratingForm)  {
        Long movieId = ratingForm.getMovieId();
        int rate = ratingForm.getRate();
        userService.addRating(request, movieId, rate);
        return null;
    }

    @PostMapping("/users/wishlist")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Rating addToWishlist(HttpServletRequest request,
                            @RequestBody Long movieId)  {
        userService.addToWishlist(request, movieId);
        return null;
    }

    @PostMapping("/users/comment")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Comment addComment(HttpServletRequest request,
                              @RequestBody CommentFormWrapper commentFormWrapper)  {
        Long movieId = commentFormWrapper.getMovieId();
        String content = commentFormWrapper.getContent();
        Long commentParentId = commentFormWrapper.getCommentParentId();

        userService.addComment(request, movieId, content, commentParentId);

        return null;
    }

    @PostMapping("/users/subscribeTo/{userId}")
    public void subscribeTo(HttpServletRequest request, @PathVariable Long userId) {
        User user = userService.findById(userId);
        userService.subscribeTo(request, userId);
    }

    @GetMapping("/users/newNotifications")
    public Boolean getIfUserHasNewNotification(HttpServletRequest request) {
        Boolean result = userService.checkIfUserHasNewNotifications(request);
        return result;
    }

    @GetMapping("/notifications")
    public List<Notification> getNotifications(HttpServletRequest request) {
        List<Notification> notifications = userService.loadNotificationsOfUser(request);
        return notifications;
    }

}
