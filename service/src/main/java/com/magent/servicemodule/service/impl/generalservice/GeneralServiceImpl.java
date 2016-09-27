package com.magent.servicemodule.service.impl.generalservice;

import com.magent.domain.interfaces.Identifiable;
import com.magent.servicemodule.service.interfaces.GeneralService;
import javassist.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * general service for CRUD operations
 *
 * @version 1.0
 * @see GeneralService interface
 * @since 25.04.2016
 * purpose for using current class
 */
class GeneralServiceImpl<T extends Identifiable> implements GeneralService<T> {
    private final JpaRepository<T, Number> repository;

    public GeneralServiceImpl(JpaRepository<T, Number> repository) {
        this.repository = repository;
    }

    /**
     * @return entity which registered in repository
     */
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return repository.findAll();
    }

    /**
     * @param id - entity id in repository
     * @return entity registered in serviceBeans.xml
     */
    @Transactional(readOnly = true)
    public T getById(Number id) throws NotFoundException {
        T entity = repository.findOne(id);
        if (entity != null) return entity;
        else throw new NotFoundException("enums is null");
    }

    /**
     * @param entity entity from registered repository for update
     * @return same entity if saved
     */
    @Transactional(rollbackFor = Exception.class)
    public T save(T entity) {
        return repository.save(entity);
    }

    /**
     * update if enums with current id present
     *
     * @param entity - entity from repository
     * @param id     - entity id from database
     * @return - same entity from registered repository if updated
     * @throws NotFoundException if can't find current entity in database
     * @implNote not use for TemplateType class
     */
    @Transactional(rollbackFor = Exception.class)
    public T update(T entity, Number id) throws NotFoundException {
        if (repository.findOne(id) != null) {
            return repository.save(entity);
        } else throw new NotFoundException("enums not present for Update");
    }

    /**
     * @param entityId - entity id from database
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(Number entityId) {
        repository.delete(entityId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(T entity) {
        repository.delete(entity);
    }

    /**
     * @param entityList - List of entities which registered in serviceBeans.xml
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveAll(List<T> entityList) {
        repository.save(entityList);
    }

    /**
     * @param entityList - List of entities which registered in serviceBeans.xml
     * @return - same list if it saved
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<T> saveAllWithReturn(List<T> entityList) {
        return repository.save(entityList);
    }

    /**
     * @param entityList - List of entities which registered in serviceBeans.xml
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll(List<T> entityList) {
        repository.delete(entityList);
    }

}
