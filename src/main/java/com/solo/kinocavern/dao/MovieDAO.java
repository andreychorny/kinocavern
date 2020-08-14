package com.solo.kinocavern.dao;

import com.solo.kinocavern.entity.Country;
import com.solo.kinocavern.entity.Movie;

import java.util.List;

public interface MovieDAO {

    public List<Movie> findAll();

    public List<Movie> findAllByParams(int pageNumber, String orderBy, Long categoryId,
                                       Long genreId);

    public Long findAmountOfElements();

    public Long findAmountOfElementsInSearchByParams(Long categoryId, Long genreId);

    public Movie findById(Long id);

    public Movie save(Movie movie);

    public void deleteById(Long id);

    public List<Movie> findByTitle(String title);

//    public List<Country> findCountriesById(Integer id);
}