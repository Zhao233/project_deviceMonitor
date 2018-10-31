package com.zx.demo.repository;

import com.zx.demo.domain.TemperatureRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemperatureRecordDao extends JpaRepository<TemperatureRecord, Long> {}
