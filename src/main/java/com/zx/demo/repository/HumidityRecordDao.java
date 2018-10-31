package com.zx.demo.repository;

import com.zx.demo.domain.HumidityRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumidityRecordDao extends JpaRepository<HumidityRecord, Long> {}
