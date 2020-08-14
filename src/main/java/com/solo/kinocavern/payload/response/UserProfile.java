package com.solo.kinocavern.payload.response;

import com.solo.kinocavern.entity.Comment;
import com.solo.kinocavern.entity.Movie;
import com.solo.kinocavern.entity.Rating;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserProfile {

    private Long id;
    private String username;

    private Set<Rating> ratings = new HashSet<>();
    private List<Movie> wishlist;
    private Set<Comment> comments = new HashSet<>();


    public UserProfile() {
    }

    public UserProfile(Long id, String username, Set<Rating> ratings,
                       List<Movie> wishlist, Set<Comment> comments) {
        this.id = id;
        this.username = username;
        this.ratings = ratings;
        this.wishlist = wishlist;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Movie> getWishlist() {
        return wishlist;
    }

    public void setWishlist(List<Movie> wishlist) {
        this.wishlist = wishlist;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}

