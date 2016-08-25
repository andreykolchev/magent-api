package com.magent.repository;

import com.magent.domain.UserPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by artomov.ihor on 14.07.2016.
 */
@Repository
public interface UserPersonalRepository extends JpaRepository<UserPersonal,Long> {

    @Query("select up from UserPersonal up where up.userId=:id and up.password=:password")
    UserPersonal findByIdAndPassword(@Param("id") Long id,@Param("password") String password);

    @Query("select usr_pers from UserPersonal usr_pers where exists (select usr from User usr where usr.id=usr_pers.userId and usr.login=:login)")
    UserPersonal getByLogin(@Param("login") String login);
}
