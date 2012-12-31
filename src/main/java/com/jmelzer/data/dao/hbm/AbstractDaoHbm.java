/*
 * Copyright (c) jmelzer 2012.
 * All rights reserved.
 */


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.AbstractDao;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractDaoHbm<T extends Serializable> implements AbstractDao<T> {

    private Class<T> clazz;

    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    public void init() {
        clazz = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T findOne(final Long id) {
        return entityManager.find(clazz, id);
//        return (T) getCurrentSession().get(clazz, id);
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("from " + clazz.getName()).getResultList();
//        return getCurrentSession().createQuery("from " + clazz.getName()).list();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void save(final T entity) {
        entityManager.persist(entity);
        entityManager.flush();
    }

    @Override
    public void update(final T entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(final T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(final Long entityId) {
        final T entity = findOne(entityId);

        delete(entity);
    }

    @Override
    public T findOneByExample(T ex) {
        Session session = (Session) entityManager.getDelegate();
        Example example = Example.create(ex);
        List list = session.createCriteria(ex.getClass()).add(example).list();
        if (list.size() > 1 ) {
            throw new IncorrectResultSizeDataAccessException(1, list.size());
        }
        if (list.size() == 1) {
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public List<String> findAllForAutoCompletion() {
        throw new RuntimeException("you must override it");
    }
    public T querySingleResult(String sql, Object param1) {
        Query query = getEntityManager().createQuery(sql);
        query.setParameter(1, param1);
        try {
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}