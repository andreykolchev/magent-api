package com.ds.repository;

import com.ds.domain.TemplateTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by artomov.ihor on 26.04.2016.
 */
@Repository
public interface TemplateTaskRepository extends JpaRepository<TemplateTask,Long> {
    @Query("select templateTasks from TemplateTask templateTasks where templateTasks.templateId=:id")
    List<TemplateTask>getTaskByTemplateId(@Param("id") Long templateId);

    @Override
    @Query("select templTask from TemplateTask templTask left join fetch templTask.template left join fetch templTask.controls" +
            " where templTask.id=:id")
    TemplateTask findOne(@Param("id") Long id);
}
