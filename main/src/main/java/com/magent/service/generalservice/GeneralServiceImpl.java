package com.magent.service.generalservice;

import com.magent.service.interfaces.GeneralService;
import javassist.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author artomov.ihor general service for CRUD operations
 * @version 1.0
 * @since 25.04.2016
 */
public class GeneralServiceImpl<T> implements GeneralService<T> {
    private final JpaRepository<T, Number> repository;

    public GeneralServiceImpl(JpaRepository<T, Number> repository) {
        this.repository = repository;
    }

    /**
     * @return enums which registered in repository
     */
    @Transactional(readOnly = true)
    public List<T> getAll() throws NotFoundException {
        return repository.findAll();
    }

    /**
     * @param id enums id in repository
     * @return enums registered in repository
     */
    @Transactional(readOnly = true)
    public T getById(Number id) throws NotFoundException {
        T entity = repository.findOne(id);
        if (entity != null) return entity;
        else throw new NotFoundException("enums is null");
    }

    /**
     * @param entity enums for update
     * @return same enums if saved
     */
    @Transactional(rollbackFor = Exception.class)
    public T save(T entity) {
        return repository.save(entity);
    }

    /**
     * update if enums with current id present
     *
     * @param entity enums from repository
     * @param id
     * @return
     * @throws NotFoundException
     * @implNote not use for TemplateType class
     */
    @Transactional(rollbackFor = Exception.class)
    public T update(T entity, Number id) throws NotFoundException {
        if (repository.findOne(id) != null) {
            return repository.save(entity);
        } else throw new NotFoundException("enums not present for Update");
    }

    /**
     * @param entityId enums id from db
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Number entityId) {
        repository.delete(entityId);
    }

    /**
     * List operatins
     *
     * @param entityList
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List<T> entityList) {
        repository.save(entityList);
    }

    /**
     * list operations
     *
     * @param entityList
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<T> entityList) {
        repository.delete(entityList);
    }

}
