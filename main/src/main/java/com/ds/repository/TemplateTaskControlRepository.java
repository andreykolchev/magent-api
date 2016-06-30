package com.ds.repository;

import com.ds.domain.TemplateTaskControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by artomov.ihor on 26.04.2016.
 */
@Repository
public interface TemplateTaskControlRepository extends JpaRepository<TemplateTaskControl,Long> {

    List<TemplateTaskControl> getByTemplateTaskId(Number id);

}
