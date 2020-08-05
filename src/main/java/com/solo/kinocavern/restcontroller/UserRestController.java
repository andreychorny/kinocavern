package com.solo.kinocavern.restcontroller;

import com.solo.kinocavern.dao.MovieDAO;
import com.solo.kinocavern.dao.UserDAO;
import com.solo.kinocavern.entity.*;
import com.solo.kinocavern.security.util.JwtUtils;
import com.solo.kinocavern.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

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
        System.out.println("wishlist");
        return null;
    }
}
