package com.ds.repository;

import com.ds.domain.SmsPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by artomov.ihor on 13.06.2016.
 */
@Repository
public interface SmsPasswordRepository extends JpaRepository<SmsPassword,Long> {

    @Query("select pass from SmsPassword pass where pass.endPeriod<:currentDate")
    List<SmsPassword> getOldSmsPass(@Param("currentDate") Date currentDate);
}
