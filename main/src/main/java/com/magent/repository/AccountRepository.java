package com.magent.repository;

import com.magent.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by artomov.ihor on 25.05.2016.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account getByUserId(Long userId);
}
