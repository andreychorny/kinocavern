package com.solo.kinocavern.restcontroller;

import com.solo.kinocavern.dao.CategoryDAO;
import com.solo.kinocavern.dao.CountryDAO;
import com.solo.kinocavern.dao.GenreDAO;
import com.solo.kinocavern.dao.MovieDAO;
import com.solo.kinocavern.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieRestController {

    @Autowired
    public MovieDAO movieDAO;

    @Autowired
    public CategoryDAO categoryDAO;

    @Autowired
    public GenreDAO genreDAO;

    @Autowired
    public CountryDAO countryDAO;

    @GetMapping("/movies")
    public List<Movie> findAll() {
        return movieDAO.findAll();
    }

    @GetMapping("/movies/{movieId}")
    public Movie getMovie(@PathVariable int movieId) {

        Movie movie = movieDAO.findById(movieId);

        if (movie == null) {
            throw new RuntimeException("Movie id not found - " + movieId);
        }

        return movie;
    }

    @PostMapping
    @RequestMapping(value = "/movies", consumes = "multipart/form-data")
    public Movie addMovie(@RequestPart("uploadFile") MultipartFile file,
                          @RequestPart("info") MovieFormWrapper model) throws IOException {
        String title = model.getTitle();
        System.out.println(title);
        List<Country> countries = new ArrayList<>();
        countries.add(countryDAO.findById(1));
        List<Genre> genres = new ArrayList<>();
        genres.add(genreDAO.findById(1));
        Movie movie = new Movie();
        movie.setId(0);
        movie.setTitle(title);
        movie.setYear(model.getYear());
        Category category = categoryDAO.findById(1);
        System.out.println("!!!!!!"+category.getName());
        System.out.println(movie.getTitle());
        movie.setCategory(categoryDAO.findById(1));
        movie.setCountries(countries);
        movie.setGenres(genres);
        movieDAO.save(movie);

        String imgUrl = "assets\\movies\\titleImg\\"+movie.getId()+movie.getTitle()+".jpg";
        Files.copy(file.getInputStream(), Paths.get("angularfront\\src\\"+imgUrl));
        movie.setImageUrl(imgUrl);
        movieDAO.save(movie);
        return movie;
    }

    @PutMapping("/movies")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Movie updateMovie(@RequestBody Movie movie) {
        movieDAO.save(movie);
        return movie;
    }

    @PutMapping("/movies/updateTitleImg")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateMovieImg(@RequestParam(value="file") MultipartFile file,
                                 @RequestParam("imgUrl") String imgUrl) throws IOException {
        Files.copy(file.getInputStream(), Paths.get("angularfront\\src\\"+imgUrl),
                StandardCopyOption.REPLACE_EXISTING);
        return "Title image successfully updated";
    }

    @DeleteMapping("/movies/{movieId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteMovie(@PathVariable int movieId) {

        Movie tempMovie = movieDAO.findById(movieId);

        if (tempMovie == null) {
            throw new RuntimeException("Movie id not found - " + movieId);
        }
        File file = new File("angularfront\\src\\"+tempMovie.getImageUrl());
        if (file!=null) file.delete();
        movieDAO.deleteById(movieId);

        return "Deleted movie id - " + movieId;
    }
}
