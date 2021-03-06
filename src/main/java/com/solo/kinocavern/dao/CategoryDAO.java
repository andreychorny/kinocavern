package com.solo.kinocavern.dao;

import com.solo.kinocavern.entity.Category;

import java.util.List;

public interface CategoryDAO {

    public List<Category> findAll();

    public Category findById(Long id);

    public void save(Category category);

}
