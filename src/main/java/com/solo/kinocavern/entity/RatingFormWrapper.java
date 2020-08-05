package com.solo.kinocavern.entity;

public class RatingFormWrapper {
    private Long movieId;
    private int rate;

    public RatingFormWrapper(Long movieId, int rate) {
        this.movieId = movieId;
        this.rate = rate;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
