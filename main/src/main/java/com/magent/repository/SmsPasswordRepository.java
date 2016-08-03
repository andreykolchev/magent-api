package com.magent.repository;

import com.magent.domain.SmsPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by artomov.ihor on 13.06.2016.
 */
@Repository
public interface SmsPasswordRepository extends JpaRepository<SmsPassword,Long> {

}
