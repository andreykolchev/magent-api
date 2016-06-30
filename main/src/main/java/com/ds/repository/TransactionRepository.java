package com.ds.repository;

import com.ds.domain.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by artomov.ihor on 27.05.2016.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {
    @Query(value = "select tr from Transactions tr where tr.transactionDate between :date1 and :date2")
    List<Transactions> getByTwoDates(@Param("date1") Date date1, @Param("date2") Date date2);
}
