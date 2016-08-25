package com.magent.repository;

import com.magent.domain.TemplateAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @since 29/04/2016
 */
@Repository
public interface TemplateAttributeRepository extends JpaRepository<TemplateAttribute, Long> {

    @Query("select tmpAttr from TemplateAttribute tmpAttr where tmpAttr.templateId=:tmplId")
    List<TemplateAttribute> findAllByTemplateId(@Param("tmplId") Long tmplId);

}
