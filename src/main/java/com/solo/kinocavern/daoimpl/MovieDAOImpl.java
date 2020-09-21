package com.solo.kinocavern.daoimpl;

import com.solo.kinocavern.dao.MovieDAO;
import com.solo.kinocavern.entity.Movie;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class MovieDAOImpl implements MovieDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Movie> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Movie> query =
                currentSession.createQuery("from Movie", Movie.class);
        List<Movie> movies = query.getResultList();
        return movies;
    }

    @Override
    @Transactional
    public List<Movie> findAllByParams(int pageNumber, String orderBy, Long categoryId,
                                       Long genreId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("SELECT m FROM Movie m " +
                "LEFT JOIN m.genres g " +
                " WHERE (:category_id is null OR m.category.id = :category_id) " +
                        " AND (:genre_id is null OR g.id = (:genre_id)) " +
                " GROUP BY m ORDER BY m." + orderBy);
        query.setParameter("category_id", categoryId);
        query.setParameter("genre_id", genreId);
        int pageSize = 12;
        query.setFirstResult(pageNumber*pageSize);
        query.setMaxResults(pageSize);
        List<Movie> movies = query.list();
        return movies;
    }

    @Override
    @Transactional
    public List<Movie> findByTitle(String title){
        Session currentSession = entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("SELECT m FROM Movie m WHERE m.title LIKE :title");
        query.setParameter("title", "%" + title + "%");
        List<Movie> movies = query.list();
        return movies;
    }

    @Override
    @Transactional
    public void updateAverageRating(Long movieId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery(" UPDATE Movie m SET m.averageRating=" +
                "(SELECT ROUND(AVG(r.rate), 2) FROM Rating r WHERE r.id.movieId=:movieId) " +
                "WHERE m.id=:movieId");
        query.setParameter("movieId", movieId);
        query.executeUpdate();
    }


    @Override
    @Transactional
    public Long findAmountOfElements() {
        Session currentSession = entityManager.unwrap(Session.class);
        String countQ = "Select count (m.id) from Movie m";
        Query countQuery = currentSession.createQuery(countQ);
        Long countedMovies = (Long) countQuery.uniqueResult();
        return countedMovies;
    }

    @Override
    @Transactional
    public Long findAmountOfElementsInSearchByParams(Long categoryId, Long genreId) {
        Session currentSession = entityManager.unwrap(Session.class);
        String countQ = "Select count(distinct m.id) from Movie m LEFT JOIN m.genres g " +
                " WHERE (:category_id is null OR m.category.id = :category_id) " +
                " AND (:genre_id is null OR g.id = (:genre_id))";
        Query countQuery = currentSession.createQuery(countQ);
        countQuery.setParameter("category_id", categoryId);
        countQuery.setParameter("genre_id", genreId);
        Long countedMovies = (Long) countQuery.uniqueResult();
        return countedMovies;
    }


    @Override
    @Transactional
    public Movie findById(Long id) {
        Session currentSession = entityManager.unwrap(Session.class);

        Movie movie = currentSession.get(Movie.class, id);
        return movie;
    }

    @Override
    @Transactional
    public Movie save(Movie movie) {

        Session currentSession = entityManager.unwrap(Session.class);
        movie = (Movie) currentSession.merge(movie);
        currentSession.saveOrUpdate(movie);
        return movie;
    }


    @Override
    @Transactional
    public void deleteById(Long idDelete) {
        Movie movie = entityManager.find(Movie.class, idDelete);
        entityManager.remove(movie);
    }

//    @Override
//    public Long getNextId(){
//        Session currentSession = entityManager.unwrap(Session.class);
//        Query query =
//                currentSession.createSQLQuery("select nextval('movie.keySequence')")
//                        .addScalar("num", StandardBasicTypes.LONG);
//        return (Long) query.uniqueResult();
//    }

//    @Override
//    public List<Country> findCountriesById(Integer id){
//        Session currentSession = entityManager.unwrap(Session.class);
//        Query<Country> query =
//                currentSession.createQuery("SELECT c FROM Movie m JOIN m.countries c " +
//                        "WHERE m.id = :movieId");
//        query.setParameter("movieId", id);
//        List<Country> countries = query.getResultList();
//        return countries;
//    }
}
