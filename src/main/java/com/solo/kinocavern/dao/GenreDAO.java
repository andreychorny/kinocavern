package com.solo.kinocavern.dao;

import com.solo.kinocavern.entity.Genre;

import java.util.List;

public interface GenreDAO {

    public List<Genre> findAll();

    public Genre findById(Long id);

    public void save(Genre genre);

    public List<Genre> findByIds(List<Long> ids);

}
