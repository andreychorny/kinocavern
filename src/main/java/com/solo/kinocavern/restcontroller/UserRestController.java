package com.solo.kinocavern.restcontroller;

import com.solo.kinocavern.dao.MovieDAO;
import com.solo.kinocavern.dao.UserDAO;
import com.solo.kinocavern.entity.*;
import com.solo.kinocavern.payload.request.CommentFormWrapper;
import com.solo.kinocavern.payload.request.RatingFormWrapper;
import com.solo.kinocavern.payload.response.UserProfile;
import com.solo.kinocavern.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class UserRestController {

    @Autowired
    public UserService userService;

    @Autowired
    public UserDAO userDAO;

    @Autowired
    private MovieDAO movieDAO;

    @GetMapping("/users/{userId}")
    public UserProfile getUserProfile(@PathVariable Long userId) {

        User user = userService.findById(userId);
        UserProfile userProfile = new UserProfile(user.getId(),user.getUsername(),user.getRatings(),
                user.getWishlist(),user.getComments());
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
        System.out.println(movieId);
        System.out.println(content);
        System.out.println(commentParentId);

        return null;
    }

}
