package com.solo.kinocavern.restcontroller;

import com.solo.kinocavern.dao.MovieDAO;
import com.solo.kinocavern.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieRestController {

    @Autowired
    public MovieDAO movieDAO;

    @GetMapping("/movies")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Movie> findAll() {
        return movieDAO.findAll();
    }

    @GetMapping("/movies/{movieId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Movie getMovie(@PathVariable int movieId) {

        Movie movie = movieDAO.findById(movieId);

        if (movie == null) {
            throw new RuntimeException("Movie id not found - " + movieId);
        }

        return movie;
    }


    @PostMapping("/movies")
    public Movie addMovie(@RequestBody Movie movie) {

        //set id to 0 in case if id will be passed in JSON
        // (it will force to save it as a new item)
        movie.setId(0);

        movieDAO.save(movie);

        return movie;
    }


    @PutMapping("/movies")
    public Movie updateMovie(@RequestBody Movie movie) {

        movieDAO.save(movie);

        return movie;
    }

    @DeleteMapping("/movies/{movieId}")
    public String deleteMovie(@PathVariable int movieId) {

        Movie tempMovie = movieDAO.findById(movieId);

        if (tempMovie == null) {
            throw new RuntimeException("Movie id not found - " + movieId);
        }

        movieDAO.deleteById(movieId);

        return "Deleted movie id - " + movieId;
    }
}
