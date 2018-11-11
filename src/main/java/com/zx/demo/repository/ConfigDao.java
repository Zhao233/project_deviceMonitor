package com.zx.demo.repository;

import com.zx.demo.domain.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigDao extends JpaRepository<Config, Long> {

    @Query(value = "select config from Config config where id=?1")
    Config getConfig(long id);
}
