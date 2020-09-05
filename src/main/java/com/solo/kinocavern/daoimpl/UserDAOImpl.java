package com.solo.kinocavern.daoimpl;

import com.solo.kinocavern.dao.UserDAO;
import com.solo.kinocavern.entity.ChatNotification;
import com.solo.kinocavern.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public User findByUsername(String username) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<User> query =
                currentSession.createQuery("FROM User u WHERE u.username = :searchedUsername");
        query.setParameter("searchedUsername", username);
        User user =  query.uniqueResult();
        return user;
    }

    @Override
    @Transactional
    public void save(User user) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(user);
    }


    @Override
    @Transactional
    public List<User> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<User> query =
                currentSession.createQuery("from User", User.class);
        List<User> users = query.getResultList();
        return users;
    }

    @Override
    @Transactional
    public User findById(Long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        User user = currentSession.get(User.class, id);
        return user;
    }

    @Override
    @Transactional
    public void test() {
        User user = findById((long) 10);
        User user2 = findById((long) 9);
        ChatNotification chatNotification = new ChatNotification();
        chatNotification.setUser(user);
        chatNotification.setUserFrom(user2);
        chatNotification.setMessage("YOU GOT MESSAGE FROM");
        chatNotification.setUnseen(true);
        entityManager.persist(chatNotification);
    }

    @Override
    @Transactional
    public Boolean usernameExists(String username) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery(
                "SELECT 1 FROM User u WHERE u.username = :searchedUsername");
        query.setParameter("searchedUsername", username);
        boolean exists = (query.uniqueResult() != null);
        return exists;
    }

    @Override
    @Transactional
    public Boolean emailExists(String email) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery(
                "SELECT 1 FROM User WHERE email = :searchedEmail");
        query.setParameter("searchedEmail", email);
        boolean exists = (query.uniqueResult() != null);
        return exists;
    }
}
