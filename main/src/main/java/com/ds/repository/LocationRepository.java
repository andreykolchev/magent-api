package com.ds.repository;

import com.ds.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by artomov.ihor on 26.04.2016.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location,Long> {
}
