package com.ds.repository;

import com.ds.domain.AssignmentAttribute;
import com.ds.domain.ValueType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by artomov.ihor on 26.04.2016.
 */
@Repository
public interface AssignmentAttributeRepository extends JpaRepository<AssignmentAttribute,Long> {

    List<AssignmentAttribute> getByAssignmentId(Number id);
    @Query("select attr from AssignmentAttribute attr where attr.assignmentId=:id and attr.valueType=:typeAttr")
    AssignmentAttribute getCommssionCost(@Param("id") Long assignId, @Param("typeAttr")ValueType valueType);
}
