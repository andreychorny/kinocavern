package com.solo.kinocavern.serviceimpl;

import com.solo.kinocavern.dao.MovieDAO;
import com.solo.kinocavern.dao.UserDAO;
import com.solo.kinocavern.entity.Movie;
import com.solo.kinocavern.entity.Rating;
import com.solo.kinocavern.entity.User;
import com.solo.kinocavern.security.util.JwtUtils;
import com.solo.kinocavern.service.MovieService;
import com.solo.kinocavern.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MovieService movieService;

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public void addRating(HttpServletRequest request, Long movieId, int rate){
        User currentUser = this.loadCurrentUser(request);
        Movie movie = movieService.findById(movieId);
        deleteRatingIfExist(movieId, currentUser);
        Rating rating = new Rating(rate, currentUser, movie);
        currentUser.getRatings().add(rating);
        userDAO.save(currentUser);

    }

    @Override
    public void addToWishlist(HttpServletRequest request, Long movieId){
        User currentUser = this.loadCurrentUser(request);
        deleteRatingIfExist(movieId, currentUser);
        Movie movie = movieService.findById(movieId);
        currentUser.addMovieToWishlist(movie);
        userDAO.save(currentUser);
        currentUser.getMovies().size();
    }
    @Override
    public User loadCurrentUser(HttpServletRequest request) {
        String token = parseJwt(request);
        String username = jwtUtils.getUserNameFromJwtToken(token);
        return userDAO.findByUsername(username);
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
}
