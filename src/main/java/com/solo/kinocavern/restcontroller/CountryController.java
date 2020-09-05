package com.solo.kinocavern.restcontroller;

import com.solo.kinocavern.entity.Country;
import com.solo.kinocavern.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CountryController {

    @Autowired
    public CountryService countryService;

    @GetMapping("/countries")
    public List<Country> findAll() {

        return countryService.findAll();
    }

}
