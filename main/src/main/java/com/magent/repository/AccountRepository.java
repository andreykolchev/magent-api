package com.magent.repository;

import com.magent.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by artomov.ihor on 25.05.2016.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account getByUserId(Long userId);

    @Query(value = "SELECT acc.* FROM ma_accounts acc WHERE exists(SELECT * FROM ma_user usr WHERE usr.usr_pk=acc.user_id AND usr.login=:login)",nativeQuery = true)
    Account getAccountByUserLogin(@Param("login")String login);
}
