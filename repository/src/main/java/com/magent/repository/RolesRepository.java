package com.magent.repository;

import com.magent.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by artomov.ihor on 07.06.2016.
 */
@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
}
