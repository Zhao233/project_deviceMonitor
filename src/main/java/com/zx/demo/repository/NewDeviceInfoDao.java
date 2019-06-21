package com.zx.demo.repository;

import com.zx.demo.domain.NewDeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewDeviceInfoDao extends JpaRepository<NewDeviceInfo, Long> {
}
