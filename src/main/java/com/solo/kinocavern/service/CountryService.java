package com.solo.kinocavern.service;

import com.solo.kinocavern.entity.Country;

import java.util.List;

public interface CountryService {

    public List<Country> findAll();

    public Country findById(Long id);

    public void save(Country country);

    public List<Country> findByIds(List<Long> ids);

}
