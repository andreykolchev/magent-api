package com.magent.repository;

import com.magent.domain.TemplateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 19.07.2016.
 */
@Repository
public interface TemplateTypeRepository extends JpaRepository<TemplateType,Long>{

    @Override
    @Query("select tmpType from TemplateType tmpType left join fetch tmpType.childTemplatesTypes where tmpType.id=:id")
    TemplateType findOne(@Param("id") Long aLong);

    @Query("select tmpType from TemplateType  tmpType where tmpType.parentId=:id")
    List<TemplateType>getAllChilds(@Param("id") Long parentId);

    @Query(value = "select distinct tmpType.* from ma_template_types tmpType where exists (select tmp from ma_template tmp where tmp.templ_pk=:id AND tmp.tmp_tmp_type_id=tmpType.temp_type_pk)",nativeQuery = true)
    TemplateType getByTemplateId(@Param("id") Long templateId);

    @Query(value = "SELECT * FROM ma_template_types tmp WHERE exists(SELECT * FROM ma_tmp_types_roles roles WHERE roles.temp_type_pk=tmp.temp_type_pk AND roles.usr_rol_pk=:userRole)",nativeQuery = true)
    List<TemplateType>getAllowedByRole(@Param("userRole")Long userRole);

}
