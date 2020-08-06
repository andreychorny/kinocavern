package com.solo.kinocavern.restcontroller;

import com.solo.kinocavern.dao.CategoryDAO;
import com.solo.kinocavern.dao.CountryDAO;
import com.solo.kinocavern.dao.GenreDAO;
import com.solo.kinocavern.dao.MovieDAO;
import com.solo.kinocavern.entity.*;
import com.solo.kinocavern.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    public UserService userService;

    @GetMapping("/movies")
    public Map<String, Object> findAllByParams(@RequestParam("page") Integer pageNumber,
                                               @RequestParam("orderBy") String orderBy,
                                               @RequestParam(required = false) Long categoryId,
                                               @RequestParam(required = false) Long genreId) {

        List<Movie> movies = movieService.findAllByParams(pageNumber, orderBy, categoryId,
                                                            genreId);
        Long amountOfElements = movieService.findAmountOfElementsInSearchByParams(categoryId, genreId);
        Map<String, Object> response = new HashMap<>();
        response.put("movies", movies);
        response.put("amountOfElements", amountOfElements);
        return response;
    }

    @GetMapping("/movies/{movieId}")
    public Map<String, Object> getMovie(HttpServletRequest request, @PathVariable Long movieId) {

        Movie movie = movieService.findById(movieId);
        Map<String, Object> response = new HashMap<>();
        response.put("movie", movie);
        if(request.getHeader("Authorization")!=null){
            User currentUser = userService.loadCurrentUser(request);
            Rating searchRating = new Rating();
            searchRating.setUser(currentUser);
            searchRating.setMovie(movie);
            int index = movie.getRatings().indexOf(searchRating);
            if(index>=0){
                Rating ratingOfLoggedUser = movie.getRatings().get(index);
                response.put("rating", ratingOfLoggedUser);
            };
            boolean movieWishlisted = currentUser.getWishlist().contains(movie);
            response.put("wishlisted", movieWishlisted);
        }
        if (movie == null) {
            throw new RuntimeException("Movie id not found - " + movieId);
        }
        return response;
    }

    @PostMapping
    @RequestMapping(value = "/movies", consumes = "multipart/form-data")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Movie addMovie(@RequestPart("uploadFile") MultipartFile file,
                          @RequestPart("info") MovieFormWrapper model) throws IOException {

        Movie savedMovie = movieService.addNewMovie(model);
        movieService.saveImage(file, savedMovie.getImageUrl());
        return savedMovie;
    }

    @PutMapping("/movies")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    public String deleteMovie(@PathVariable  Long movieId) {

        movieService.deleteById(movieId);

        return "Deleted movie id - " + movieId;
    }
}
