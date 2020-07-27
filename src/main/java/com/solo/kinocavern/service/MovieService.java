package com.solo.kinocavern.service;

import com.solo.kinocavern.entity.Movie;
import com.solo.kinocavern.entity.MovieFormWrapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    public List<Movie> findAll();

    public List<Movie> findAllByPage(int pageNumber);

    public Long findAmountOfElements();

    public Movie findById(int id);

    public Movie save(Movie movie);

    public void deleteById(int id);

    public Movie addNewMovie(MovieFormWrapper model);

    public void saveImage(MultipartFile file, String imgUrl) throws IOException;

    public void deleteImageFromDirectory(String imgUrl);
}
