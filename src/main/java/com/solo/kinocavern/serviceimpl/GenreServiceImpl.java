package com.solo.kinocavern.serviceimpl;

import com.solo.kinocavern.dao.GenreDAO;
import com.solo.kinocavern.entity.Genre;
import com.solo.kinocavern.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    public GenreDAO genreDAO;

    @Override
    public List<Genre> findAll() {
        return genreDAO.findAll();
    }

    @Override
    public Genre findById(int id) {
        return genreDAO.findById(id);
    }

    @Override
    public void save(Genre genre) {
        genreDAO.save(genre);
    }

    @Override
    public List<Genre> findByIds(List<Integer> ids) {
        return genreDAO.findByIds(ids);
    }
}
