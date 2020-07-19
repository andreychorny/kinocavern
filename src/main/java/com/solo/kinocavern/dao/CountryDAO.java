package com.solo.kinocavern.dao;

import com.solo.kinocavern.entity.Country;

import java.util.List;

public interface CountryDAO {

    public List<Country> findAll();

    public Country findById(int id);

    public void save(Country country);

}
