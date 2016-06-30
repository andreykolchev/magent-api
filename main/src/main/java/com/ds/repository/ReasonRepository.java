package com.ds.repository;

import com.ds.domain.Reason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author artomov.ihor
 * @since 29/04/2016
 */
@Repository
public interface ReasonRepository extends JpaRepository<Reason, Long> {

    @Query("select reasons from Reason reasons where reasons.parentId=:parentId")
    List<Reason> getListByParentId(@Param("parentId") Long parentId);

    @Override
    @Query("select reason from Reason reason left join fetch reason.parent where reason.id=:id")
    Reason findOne(@Param("id") Long id);
}
