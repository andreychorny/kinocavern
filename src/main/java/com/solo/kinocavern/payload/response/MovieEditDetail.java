package com.solo.kinocavern.payload.response;

import com.solo.kinocavern.entity.*;

import java.util.List;

public class MovieEditDetail {


    private Long id;

    private String title;

    private int year;

    private String imageUrl;

    private Category category;

    private List<Country> countries;

    private List<Genre> genres;

    private List<Comment> comments;

    private String description;

    public MovieEditDetail(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.year = movie.getYear();
        this.imageUrl = movie.getImageUrl();
        this.category = movie.getCategory();
        this.countries = movie.getCountries();
        this.genres = movie.getGenres();
        this.comments = movie.getComments();
        this.description = movie.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
