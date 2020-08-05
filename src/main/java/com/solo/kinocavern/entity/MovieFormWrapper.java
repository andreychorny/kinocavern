package com.solo.kinocavern.entity;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class MovieFormWrapper {

    private String title;
    private Integer year;
    private List<Long> genresIds;
    private List<Long> countriesIds;
    private Long categoryId;

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

    public List<Long> getGenresIds() {
        return genresIds;
    }

    public void setGenresIds(List<Long> genresIds) {
        this.genresIds = genresIds;
    }

    public List<Long> getCountriesIds() {
        return countriesIds;
    }

    public void setCountriesIds(List<Long> countriesIds) {
        this.countriesIds = countriesIds;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
