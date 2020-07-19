package com.solo.kinocavern.entity;

import org.springframework.web.multipart.MultipartFile;

public class MovieFormWrapper {
    private MultipartFile image;
    private String title;
    private Integer year;

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

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
}
