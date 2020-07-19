package com.solo.kinocavern.dao;

import com.solo.kinocavern.entity.Genre;

import java.util.List;

public interface GenreDAO {

    public List<Genre> findAll();

    public Genre findById(int id);

    public void save(Genre genre);

}
