package com.solo.kinocavern.restcontroller;

import com.solo.kinocavern.dao.CategoryDAO;
import com.solo.kinocavern.dao.CountryDAO;
import com.solo.kinocavern.dao.GenreDAO;
import com.solo.kinocavern.dao.MovieDAO;
import com.solo.kinocavern.entity.*;
import com.solo.kinocavern.service.CategoryService;
import com.solo.kinocavern.service.CountryService;
import com.solo.kinocavern.service.GenreService;
import com.solo.kinocavern.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieRestController {

    @Autowired
    public MovieService movieService;

    @Autowired
    public CategoryService categoryService;

    @Autowired
    public GenreService genreService;

    @Autowired
    public CountryService countryService;

    @GetMapping("/movies")
    public Map<String, Object> findAll(@RequestParam("page") Integer pageNumber) {
        List<Movie> movies = movieService.findAllByPage(pageNumber);
        Long amountOfElements = movieService.findAmountOfElements();
        Map<String, Object> results = new HashMap<>();
        results.put("movies", movies);
        results.put("amountOfElements", amountOfElements);
        return results;
    }


    @GetMapping("/movies/{movieId}")
    public Movie getMovie(@PathVariable int movieId) {

        Movie movie = movieService.findById(movieId);

        if (movie == null) {
            throw new RuntimeException("Movie id not found - " + movieId);
        }

        return movie;
    }

    @PostMapping
    @RequestMapping(value = "/movies", consumes = "multipart/form-data")
    public Movie addMovie(@RequestPart("uploadFile") MultipartFile file,
                          @RequestPart("info") MovieFormWrapper model) throws IOException {

        Movie savedMovie = movieService.addNewMovie(model);
        movieService.saveImage(file, savedMovie.getImageUrl());
        return savedMovie;
    }

    @PutMapping("/movies")
    public Movie updateMovie(@RequestBody Movie movie) {
        movieService.save(movie);
        return movie;
    }

    @PutMapping("/movies/updateTitleImg")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateMovieImg(@RequestParam(value="file") MultipartFile file,
                                 @RequestParam("imgUrl") String imgUrl) throws IOException {
        movieService.saveImage(file, imgUrl);
        return "Title image successfully updated";
    }

    @DeleteMapping("/movies/{movieId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteMovie(@PathVariable int movieId) {

        movieService.deleteById(movieId);

        return "Deleted movie id - " + movieId;
    }
}
