package com.zx.demo.repository;

import com.zx.demo.domain.Device;
import com.zx.demo.domain.Temp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TempDao extends JpaRepository<Temp, Long> {

}
