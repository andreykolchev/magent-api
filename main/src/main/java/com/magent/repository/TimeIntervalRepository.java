package com.magent.repository;

import com.magent.domain.TimeInterval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created on 02.08.2016.
 */
@Repository
public interface TimeIntervalRepository extends JpaRepository<TimeInterval,Integer> {
    @Query("select distinct inter from TimeInterval inter where inter.name=:name")
    TimeInterval getByName(@Param("name") String name);
}
