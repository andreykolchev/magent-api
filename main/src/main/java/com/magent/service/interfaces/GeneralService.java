package com.magent.service.interfaces;

import javassist.NotFoundException;

import java.util.List;

/**
 * Created by artomov.ihor on 26.04.2016.
 */
public interface GeneralService<T> {
    public List<T> getAll() throws NotFoundException;
    public T getById(Number id) throws NotFoundException;
    public T save(T entity);
    public T update(T entity, Number id) throws NotFoundException;
    public void delete(Number entityId);
    public void saveAll(List<T> entityList);
    public void deleteAll(List<T> entityList);

}
