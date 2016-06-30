package com.ds.repository;

import com.ds.domain.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by artomov.ihor on 26.04.2016.
 */
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment,Long> {

    @Override
    @Query("select assign from Assignment assign left join fetch assign.tasks left join fetch assign.callList " +
            " left join fetch assign.attributes left join fetch assign.user  left join fetch assign.template where assign.id=:id")
    Assignment findOne(@Param("id") Long id);


    @Query("select distinct assign from Assignment assign "+
            " left join fetch assign.attributes" +
            " left join fetch assign.tasks " +
            " where assign.userId =:userId")
    List<Assignment> findAllByUserId(@Param("userId") Long userId);


    @Query("select distinct assign from Assignment assign " +
           " left join fetch assign.attributes " +
           " left join fetch assign.tasks" +
           " where assign.userId=:userId and assign.lastChange>:syncId")
    List<Assignment> findAllByUserIdAndLastChange(@Param("userId") Long userId, @Param("syncId") Long syncId);


}
