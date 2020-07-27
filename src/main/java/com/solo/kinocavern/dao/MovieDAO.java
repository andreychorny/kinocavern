package com.solo.kinocavern.dao;

import com.solo.kinocavern.entity.Country;
import com.solo.kinocavern.entity.Movie;

import java.util.List;

public interface MovieDAO {

    public List<Movie> findAll();

    public List<Movie> findAllByPage(int pageNumber);

    public Long findAmountOfElements();

    public Movie findById(int id);

    public Movie save(Movie movie);

    public void deleteById(int id);

//    public List<Country> findCountriesById(Integer id);
}