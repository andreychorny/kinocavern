package com.solo.kinocavern.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "rating")
public class Rating {

    @EmbeddedId
    private RatingId id;

    @Column(name="rate")
    private int rate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    @MapsId("userId")
    @JsonBackReference
    private User user;

    @ManyToOne()
    @MapsId("movieId")
    private Movie movie;

    public Rating() {
    }

    public Rating(int rate, User user, Movie movie) {
        this.id = new RatingId(user.getId(), movie.getId());
        this.rate = rate;
        this.user = user;
        this.movie = movie;
    }

    public Long getUserId(){
        return id.getUserId();
    }

    public Long getMovieId(){
        return id.getMovieId();
    }

    public RatingId getId() {
        return id;
    }

    public void setId(RatingId id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }


    //we do equals ignoring 'rate' because we need it only to check if the rating exist
    //and we can't have 2 instances with same User and Movie anyway

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return user.equals(rating.user) &&
                movie.equals(rating.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, movie);
    }
}
