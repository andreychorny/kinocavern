package com.solo.kinocavern.entity;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class MovieFormWrapper {
    private String title;
    private Integer year;
    private List<Integer> genresIds;
    private List<Integer> countriesIds;
    private Integer categoryId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Integer> getGenresIds() {
        return genresIds;
    }

    public void setGenresIds(List<Integer> genresIds) {
        this.genresIds = genresIds;
    }

    public List<Integer> getCountriesIds() {
        return countriesIds;
    }

    public void setCountriesIds(List<Integer> countriesIds) {
        this.countriesIds = countriesIds;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
