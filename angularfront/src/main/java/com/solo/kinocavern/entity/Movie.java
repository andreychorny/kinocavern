package com.solo.kinocavern.entity;

import javax.persistence.*;

@Entity
@Table(name="movie")
public class Movie {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="year")
    private int year;

    @Column(name="country")
    private String country;

    public Movie() {

    }

    public Movie(int id, String title, int year, String country) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
