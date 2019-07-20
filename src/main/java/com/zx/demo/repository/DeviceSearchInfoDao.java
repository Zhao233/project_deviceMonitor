package com.zx.demo.repository;

import com.zx.demo.domain.DeviceSearchInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DeviceSearchInfoDao extends JpaRepository<DeviceSearchInfo, Long> {

    /**管理员*/
    @Query(value = "SELECT deviceSearchInfo FROM DeviceSearchInfo deviceSearchInfo")
    Page<DeviceSearchInfo> getAll(Pageable pageable);
}
