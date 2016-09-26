package com.magent.servicemodule.service.interfaces;

import javassist.NotFoundException;

import java.util.List;

/**
 * Created by artomov.ihor on 26.04.2016.
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
