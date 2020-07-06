package com.solo.kinocavern.daoimpl;

import com.solo.kinocavern.dao.RoleDAO;
import com.solo.kinocavern.entity.EnumRole;
import com.solo.kinocavern.entity.Role;
import com.solo.kinocavern.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public Role findByName(EnumRole eRole) {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Role> query =
                currentSession.createQuery("FROM Role r WHERE r.name = :enumRole");
        query.setParameter("enumRole", eRole);
        Role role =  query.uniqueResult();
        return role;
    }

}
