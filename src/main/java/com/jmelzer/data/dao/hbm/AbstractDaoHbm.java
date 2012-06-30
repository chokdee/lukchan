/*
 * Copyright (c) jmelzer 2012.
 * All rights reserved.
 */


package com.jmelzer.data.dao.hbm;

import com.jmelzer.data.dao.AbstractDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractDaoHbm<T extends Serializable> implements AbstractDao<T> {

    private Class<T> clazz;

    @Resource(name = "hibernateSessionFactory")
    SessionFactory sessionFactory;

    @PostConstruct
    public void init() {
        clazz = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T findOne(final Long id) {
        return (T) getCurrentSession().get(clazz, id);
    }

    @Override
    public List<T> findAll() {
        return getCurrentSession().createQuery("from " + clazz.getName()).list();
    }

    @Override
    public void save(final T entity) {
        getCurrentSession().persist(entity);
        getCurrentSession().flush();
    }

    @Override
    public void update(final T entity) {
        getCurrentSession().merge(entity);
    }

    @Override
    public void delete(final T entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void deleteById(final Long entityId) {
        final T entity = findOne(entityId);

        delete(entity);
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public T findOneByExample(T ex) {
        Example example = Example.create(ex);
        List list = getCurrentSession().createCriteria(ex.getClass()).add(example).list();
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
}