package com.solo.kinocavern.serviceimpl;

import com.solo.kinocavern.dao.CountryDAO;
import com.solo.kinocavern.entity.Country;
import com.solo.kinocavern.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    public CountryDAO countryDAO;

    @Override
    public List<Country> findAll() {
        return countryDAO.findAll();
    }

    @Override
    public Country findById(Long id) {
        return countryDAO.findById(id);
    }

    @Override
    public void save(Country country) {
        countryDAO.save(country);
    }

    @Override
    public List<Country> findByIds(List<Long> ids) {
        return countryDAO.findByIds(ids);
    }
}
