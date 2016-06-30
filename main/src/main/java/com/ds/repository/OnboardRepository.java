package com.ds.repository;

import com.ds.domain.OnBoarding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by artomov.ihor on 22.06.2016.
 */
@Repository
public interface OnboardRepository extends JpaRepository<OnBoarding,Long> {
}
