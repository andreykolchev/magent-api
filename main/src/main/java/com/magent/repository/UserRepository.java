package com.magent.repository;

import com.magent.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by artomov.ihor on 26.04.2016.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    @Query("select user from User user left join fetch user.devices where user.id=:id")
    User findOne(@Param("id") Long id);

    @Query("SELECT u FROM User u WHERE u.id = :id OR u.login = :login OR u.role  = :role")
    List<User> findUsersByFilter(@Param("id") Long id, @Param("login") String login, @Param("role") Long role);

    User findByLogin(String login);

    @Query("select users from User users where exists (select acc from Account acc where acc.userId=users.id)")
    List<User>getAllUsersWithAccount();

}
