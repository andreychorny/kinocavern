package com.solo.kinocavern.dto.response;

import com.solo.kinocavern.entity.Comment;
import com.solo.kinocavern.entity.Movie;
import com.solo.kinocavern.entity.Rating;
import com.solo.kinocavern.entity.User;

import java.util.HashSet;
import java.util.Set;

public class UserProfile {

    private Long id;
    private String username;

    private Set<Rating> ratings = new HashSet<>();
    private Set<Movie> wishlist;
    private Set<Comment> comments = new HashSet<>();
    private Set<User> subscribers;
    private Set<User> subscribtions;
    
    public UserProfile() {
    }

    public UserProfile(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.ratings = user.getRatings();
        this.wishlist = user.getWishlist();
        this.comments = user.getComments();
        this.subscribers = user.getSubscribers();
        this.subscribtions = user.getSubscriptions();
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

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Movie> getWishlist() {
        return wishlist;
    }

    public void setWishlist(Set<Movie> wishlist) {
        this.wishlist = wishlist;
    }

    public Set<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<User> subscribers) {
        this.subscribers = subscribers;
    }

    public Set<User> getSubscribtions() {
        return subscribtions;
    }

    public void setSubscribtions(Set<User> subscribtions) {
        this.subscribtions = subscribtions;
    }
}

