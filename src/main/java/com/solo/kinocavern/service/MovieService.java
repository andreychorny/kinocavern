package com.solo.kinocavern.service;

import com.solo.kinocavern.entity.Movie;
import com.solo.kinocavern.dto.request.MovieFormWrapper;
import com.solo.kinocavern.dto.response.MovieDetail;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface MovieService {

    public List<Movie> findAll();

    public List<Movie> findAllByParams(int pageNumber, String orderBy, Long categoryId,
                                       Long genreId);

    public Long findAmountOfElementsInSearchByParams(Long categoryId, Long genreId);

    public Movie findById(Long id);

    public MovieDetail getMovieDetail(HttpServletRequest request, Long movieId);

    public List<Movie> findByTitle(String title);

    public Movie save(Movie movie);

    public void deleteById(Long id);

    public Movie addNewMovie(MovieFormWrapper model);

    public void saveImage(MultipartFile file, String imgUrl) throws IOException;

    public void deleteImageFromDirectory(String imgUrl);

    public void updateAverageRating(Long movieId);
}
