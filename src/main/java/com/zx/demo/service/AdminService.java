package com.zx.demo.service;

import com.zx.demo.domain.Admin;
import com.zx.demo.repository.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminService {
    Admin findByAdminId(Long id);

    void update(Admin admin);


    List<Admin> getAll();

    void delete(Admin admin);

}
