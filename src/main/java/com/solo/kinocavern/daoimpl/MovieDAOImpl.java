package com.solo.kinocavern.daoimpl;

import com.solo.kinocavern.dao.MovieDAO;
import com.solo.kinocavern.entity.Country;
import com.solo.kinocavern.entity.Movie;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.type.StandardBasicTypes;
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
    public List<Movie> findAllByPage(int pageNumber) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("from Movie");
        int pageSize = 12;
        query.setFirstResult(pageNumber*pageSize);
        query.setMaxResults(pageSize);
        List<Movie> movies = query.list();
        return movies;
    }

    @Override
    public Long findAmountOfElements() {
        Session currentSession = entityManager.unwrap(Session.class);
        String countQ = "Select count (m.id) from Movie m";
        Query countQuery = currentSession.createQuery(countQ);
        Long countedMovies = (Long) countQuery.uniqueResult();
        return countedMovies;
    }

    @Override
    @Transactional
    public Movie findById(int id) {
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
    public void deleteById(int idDelete) {

        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createQuery(
                        "delete from Movie where id=:idDel");
        theQuery.setParameter("idDel", idDelete);

        theQuery.executeUpdate();
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
