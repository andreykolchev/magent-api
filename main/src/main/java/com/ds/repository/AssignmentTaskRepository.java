package com.ds.repository;

import com.ds.domain.AssignmentTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by artomov.ihor on 26.04.2016.
 */
@Repository
public interface AssignmentTaskRepository extends JpaRepository<AssignmentTask,Long> {

    List<AssignmentTask> getByAssignmentId(Number id);

}
