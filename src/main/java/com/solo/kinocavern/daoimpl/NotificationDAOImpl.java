package com.solo.kinocavern.daoimpl;

import com.solo.kinocavern.dao.NotificationDAO;
import com.solo.kinocavern.entity.Movie;
import com.solo.kinocavern.entity.Notification;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class NotificationDAOImpl implements NotificationDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public void save(Notification notification) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(notification);

    }

    @Override
    public Boolean checkIfUserIdHasNewNotifications(Long currentUserId) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Notification> query =
                currentSession.createQuery("from Notification n WHERE n.user.id =:currentUserId AND " +
                        "n.isUnseen is true", Notification.class);
        query.setParameter("currentUserId", currentUserId);
        List<Notification> notifications = query.getResultList();
        if(notifications.size()>0) return true;
        return false;
    }
}
