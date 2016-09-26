package com.magent.servicemodule.service.interfaces;

import javassist.NotFoundException;

import java.util.List;

/**
 * Created by artomov.ihor on 26.04.2016.
 * Current interface should be used as general CRUD operations for all entity @see magent.model module with transactions.
 * Transaction isolation and propagation look into implementation.
 * For registration bean as generalService you should register bean into serviceBeans.xml
 * implementation @see com.magent.servicemodule.service.impl.generalservice.GeneralServiceImpl class
 */
public interface GeneralService<T> {

    List<T> getAll() throws NotFoundException;

    T getById(Number id) throws NotFoundException;

    T save(T entity);

    T update(T entity, Number id) throws NotFoundException;

    void delete(Number entityId);

    void delete(T entity);

    void saveAll(List<T> entityList);

    void deleteAll(List<T> entityList);

}
