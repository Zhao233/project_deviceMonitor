package com.zx.demo.service.implement;

import com.zx.demo.domain.Config;
import com.zx.demo.repository.ConfigDao;
import com.zx.demo.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("configService")
public class ConfigServiceImp implements ConfigService {
    @Autowired
    private ConfigDao configDao;
    @Override
    public void save(Config config) {
        configDao.save(config);
    }
}
