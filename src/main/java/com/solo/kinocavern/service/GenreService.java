package com.solo.kinocavern.service;

import com.solo.kinocavern.entity.Genre;

import java.util.List;

public interface GenreService {

    public List<Genre> findAll();

    public Genre findById(int id);

    public void save(Genre genre);

    public List<Genre> findByIds(List<Integer> ids);

}
