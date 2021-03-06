package com.solo.kinocavern.daoimpl;

import com.solo.kinocavern.dao.CountryDAO;
import com.solo.kinocavern.entity.Country;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class CountryDAOImpl implements CountryDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Country> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Country> query =
                currentSession.createQuery("from Country", Country.class);
        List<Country> countries = query.getResultList();
        return countries;
    }

    @Override
    @Transactional
    public Country findById(Long id) {
        Session currentSession = entityManager.unwrap(Session.class);
        Country country = currentSession.get(Country.class, id);
        return country;
    }

    @Override
    @Transactional
    public void save(Country country) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(country);

    }

    @Override
    @Transactional
    public List<Country> findByIds(List<Long> ids){
        Session currentSession = entityManager.unwrap(Session.class);
        Query query = currentSession.createQuery("from Country where id IN :idList");
        query.setParameterList("idList", ids);
        return  query.list();
    }
}
