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
    public Movie findById(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Movie movie = currentSession.get(Movie.class, id);
        return movie;
    }

    @Override
    @Transactional
    public void save(Movie movie) {

        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(movie);

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
}
