package com.magent.repository;

import com.magent.domain.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by artomov.ihor on 26.04.2016.
 */
@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {

    Settings findByUserId(Long userId);
}
