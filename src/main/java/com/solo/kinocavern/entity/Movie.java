package com.solo.kinocavern.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.*;

@Entity
@Table(name="movie")
public class Movie {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="year")
    private int year;

    @Column(name="imageurl")
    private String imageUrl;

    @Column(name="description")
    private String description;

    @Column(name="avg_rating")
    private Double averageRating;

    @ManyToOne(fetch=FetchType.EAGER, cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name="movie_country",
            joinColumns=@JoinColumn(name="movie_id"),
            inverseJoinColumns=@JoinColumn(name="country_id")
    )
    private List<Country> countries;

    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name="movie_genre",
            joinColumns=@JoinColumn(name="movie_id"),
            inverseJoinColumns=@JoinColumn(name="genre_id")
    )
    private List<Genre> genres;

    @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name="wishlist",
            joinColumns=@JoinColumn(name="movie_id"),
            inverseJoinColumns=@JoinColumn(name="user_id")
    )
    @JsonIgnore
    private Set<User> usersWhichWishlisted = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "movie",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    @JsonBackReference
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "movie",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();


    public Movie() {

    }


    public Movie(Long id, String title, int year, String imageUrl, String description,
                 Category category, List<Country> countries, List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.imageUrl = imageUrl;
        this.description = description;
        this.category = category;
        this.countries = countries;
        this.genres = genres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    /////////

    public void addGenre(Genre genre) {

        if (genres == null) {
            genres = new ArrayList<Genre>();
        }

        genres.add(genre);
    }

    public void addCountry(Country country) {

        if (countries == null) {
            countries = new ArrayList<Country>();
        }
        countries.add(country);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return year == movie.year &&
                id.equals(movie.id) &&
                title.equals(movie.title) &&
                Objects.equals(imageUrl, movie.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, year, imageUrl);
    }
}
