package com.solo.kinocavern.dao;

import com.solo.kinocavern.entity.Movie;

import java.util.List;

public interface MovieDAO {

    public List<Movie> findAll();

    public Movie findById(int id);

    public void save(Movie movie);

    public void deleteById(int id);

    public Long getNextId();

}
