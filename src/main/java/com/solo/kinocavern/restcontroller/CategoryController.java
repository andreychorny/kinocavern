package com.solo.kinocavern.restcontroller;

import com.solo.kinocavern.dao.CategoryDAO;
import com.solo.kinocavern.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    @Autowired
    public CategoryDAO categoryDAO;

    @GetMapping("/categories")
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

}
