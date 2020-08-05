package com.solo.kinocavern.dao;

import com.solo.kinocavern.entity.Country;
import com.solo.kinocavern.entity.Genre;

import java.util.List;

public interface CountryDAO {

    public List<Country> findAll();

    public Country findById(Long id);

    public void save(Country country);

    public List<Country> findByIds(List<Long> ids);

}
