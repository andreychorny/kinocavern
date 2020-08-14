package com.solo.kinocavern.daoimpl;

import com.solo.kinocavern.dao.CategoryDAO;
import com.solo.kinocavern.entity.Category;
import com.solo.kinocavern.entity.Movie;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Category> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Category> query =
                currentSession.createQuery("from Category", Category.class);
        List<Category> categories = query.getResultList();
        return categories;
    }

    @Override
    @Transactional
    public Category findById(Long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Category category = currentSession.get(Category.class, id);
        return category;
    }

    @Override
    @Transactional
    public void save(Category category) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(category);
    }
}
