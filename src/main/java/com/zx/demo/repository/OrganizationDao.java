package com.zx.demo.repository;

import com.zx.demo.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationDao extends JpaRepository<Organization, Long> {}
