package com.solo.kinocavern.daoimpl;

import com.solo.kinocavern.dao.CommentDAO;
import com.solo.kinocavern.entity.Comment;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public class CommentDAOImpl implements CommentDAO {

    @Autowired
    private EntityManager entityManager;


    @Override
    @Transactional
    public void save(Comment comment) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(comment);

    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query theQuery = currentSession.createQuery(
                "delete from Comment where id=:id or parentId=:id");
        theQuery.setParameter("id", id);
        theQuery.executeUpdate();
    }
}
