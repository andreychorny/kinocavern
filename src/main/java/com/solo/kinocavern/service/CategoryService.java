package com.solo.kinocavern.service;

import com.solo.kinocavern.entity.Category;

import java.util.List;

public interface CategoryService {

    public List<Category> findAll();

    public Category findById(Long id);

    public void save(Category category);

}
