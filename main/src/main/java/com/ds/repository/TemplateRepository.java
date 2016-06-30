package com.ds.repository;

import com.ds.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by artomov.ihor on 26.04.2016.
 */
@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    @Override
    @Query("select tmp from Template tmp left join fetch tmp.attributes left join fetch tmp.templateTasks where tmp.id=:id")
    Template findOne(@Param("id") Long id);

}
