package com.magent.repository;

import com.magent.domain.TemporaryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by artomov.ihor on 08.07.2016.
 */
@Repository
public interface TemporaryUserRepository extends JpaRepository<TemporaryUser, Long> {

    @Query("select tmp from TemporaryUser tmp where tmp.username=:login")
    TemporaryUser getByLogin(@Param("login") String login);

    @Query("select tmp from TemporaryUser tmp where tmp.username=:login and tmp.hashedOtp=:otp")
    TemporaryUser getByLoginAndOtp(@Param("login")String login,@Param("otp")String otp);
}
