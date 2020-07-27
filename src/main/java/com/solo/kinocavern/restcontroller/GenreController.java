package com.solo.kinocavern.restcontroller;

import com.solo.kinocavern.dao.GenreDAO;
import com.solo.kinocavern.entity.Category;
import com.solo.kinocavern.entity.Genre;
import com.solo.kinocavern.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class GenreController {

    @Autowired
    public GenreService genreService;

    @GetMapping("/genres")
    public List<Genre> findAll() {
        return genreService.findAll();
    }

}
