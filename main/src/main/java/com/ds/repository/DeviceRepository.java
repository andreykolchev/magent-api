package com.ds.repository;

import com.ds.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by artomov.ihor on 26.04.2016.
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device,String> {
}
