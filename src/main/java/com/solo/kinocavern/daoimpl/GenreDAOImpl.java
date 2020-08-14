package com.solo.kinocavern.daoimpl;

import com.solo.kinocavern.dao.GenreDAO;
import com.solo.kinocavern.entity.Category;
import com.solo.kinocavern.entity.Genre;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class GenreDAOImpl implements GenreDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Genre> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Genre> query =
                currentSession.createQuery("from Genre", Genre.class);
        List<Genre> genres = query.getResultList();
        return genres;
    }

    @Override
    @Transactional
    public Genre findById(Long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Genre genre = currentSession.get(Genre.class, id);
        return genre;
    }

    @Override
    @Transactional
    public void save(Genre genre) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(genre);
    }


    @Override
    @Transactional
    public List<Genre> findByIds(List<Long> ids){
        Session currentSession = entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("from Genre where id IN :idList");
        query.setParameterList("idList", ids);
        return  query.list();
    }
}
