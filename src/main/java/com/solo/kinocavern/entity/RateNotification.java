package com.solo.kinocavern.entity;

import javax.persistence.*;

@Entity
@Table(name = "rate_notification")
public class RateNotification extends Notification {

    @ManyToOne(fetch= FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="user_from_id")
    private User userFrom;

    @Column(name="message")
    private String message;

    @ManyToOne(fetch= FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="movie_id")
    private Movie movie;

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
