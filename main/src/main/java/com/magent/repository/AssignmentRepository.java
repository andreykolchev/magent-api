package com.magent.repository;

import com.magent.domain.Assignment;
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


    @Query(value = "SELECT DISTINCT ass.* FROM ma_assignment ass LEFT JOIN ma_assignment_attribute attr ON ass.assign_pk=attr.assignment_id LEFT JOIN ma_assignment_tasks task ON ass.assign_pk=task.assignment_id  WHERE  ass.lastchange>:syncId OR ass.lastchange ISNULL AND ass.user_id=:userId",nativeQuery = true)
    List<Assignment> findAllByUserIdAndLastChange(@Param("userId") Long userId, @Param("syncId") Long syncId);


}
