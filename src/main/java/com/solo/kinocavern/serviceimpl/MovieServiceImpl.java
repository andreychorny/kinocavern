package com.solo.kinocavern.serviceimpl;

import com.solo.kinocavern.dao.MovieDAO;
import com.solo.kinocavern.entity.*;
import com.solo.kinocavern.dto.request.MovieFormWrapper;
import com.solo.kinocavern.dto.response.MovieDetail;
import com.solo.kinocavern.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private CountryService countryService;

    @Override
    public Movie addNewMovie(MovieFormWrapper model) {

        // in case of id=0, Hibernate's save/update will perform saving of new record
        Long id = (long) 0;
        String title = model.getTitle();
        int year = model.getYear();
        String description = model.getDescription();
        List<Long> genresIds = model.getGenresIds();
        List<Long> countriesIds = model.getCountriesIds();
        Long categoryId = model.getCategoryId();
        List<Genre> genres = genreService.findByIds(genresIds);
        List<Country> countries = countryService.findByIds(countriesIds);
        Category category = categoryService.findById(categoryId);

        // We generate image url after getting an ID from database, so for now it's null
        Movie movie = new Movie(id, title, year, null, description, category, countries, genres);
        movie = this.save(movie);

        String imgUrl = "assets\\movies\\titleImg\\"+movie.getId()+movie.getTitle().trim()+".jpg";
        movie.setImageUrl(imgUrl);
        //updating movie for adding imageUrl
        movie = this.save(movie);

        return movie;
    }

    @Override
    public void saveImage(MultipartFile file, String imgUrl) throws IOException {
        Files.copy(file.getInputStream(), Paths.get("angularfront\\src\\"+imgUrl),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public List<Movie> findAll() {
        return movieDAO.findAll();
    }

    @Override
    public List<Movie> findAllByParams(int pageNumber, String orderBy, Long categoryId,
                                       Long genreId) {
        if(orderBy == null) orderBy = "id";
        return movieDAO.findAllByParams(pageNumber, orderBy, categoryId, genreId);
    }

    @Override
    public Long findAmountOfElementsInSearchByParams(Long categoryId, Long genreId) {
        return movieDAO.findAmountOfElementsInSearchByParams(categoryId, genreId);
    }

    @Override
    public Movie findById(Long id) {
        return movieDAO.findById(id);
    }

    @Override
    public MovieDetail getMovieDetail(HttpServletRequest request, Long movieId){
        Movie movie = this.findById(movieId);
        MovieDetail movieDetail = new MovieDetail(movie);

        //load current User data according this movie if user is logged in
        if(request.getHeader("Authorization")!=null){
            User currentUser = userService.loadCurrentUser(request);
            Rating searchRating = new Rating();
            searchRating.setUser(currentUser);
            searchRating.setMovie(movie);
            int index = movie.getRatings().indexOf(searchRating);
            if(index>=0){
                Rating ratingOfLoggedUser = movie.getRatings().get(index);
                movieDetail.setRating(ratingOfLoggedUser);
            };
            boolean movieWishlisted = currentUser.getWishlist().contains(movie);
            movieDetail.setWishlisted(movieWishlisted);
        }
        if (movie == null) {
            throw new RuntimeException("Movie id not found - " + movieId);
        }
        return movieDetail;
    }

    @Override
    public List<Movie> findByTitle(String title) {
        return movieDAO.findByTitle(title);
    }

    @Override
    public Movie save(Movie movie) {
        return movieDAO.save(movie);
    }

    @Override
    public void deleteById(Long movieId) {

        Movie tempMovie = movieDAO.findById(movieId);

        if (tempMovie == null) {
            throw new RuntimeException("Movie id not found - " + movieId);
        }

        this.deleteImageFromDirectory(tempMovie.getImageUrl());

        movieDAO.deleteById(movieId);

    }

    @Override
    public void deleteImageFromDirectory(String imgUrl){
        File file = new File("angularfront\\src\\"+imgUrl);
        if (file!=null) file.delete();
    }

    @Override
    public void updateAverageRating(Long movieId){
        movieDAO.updateAverageRating(movieId);
    }
}
