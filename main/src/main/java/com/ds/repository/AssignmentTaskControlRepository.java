package com.ds.repository;

import com.ds.domain.AssignmentTaskControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by artomov.ihor on 26.04.2016.
 */
@Repository
public interface AssignmentTaskControlRepository extends JpaRepository<AssignmentTaskControl, Long> {

    List<AssignmentTaskControl> getByAssignmentTaskId(Number id);

}
