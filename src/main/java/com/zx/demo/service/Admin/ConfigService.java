package com.zx.demo.service.Admin;

import com.zx.demo.domain.Config;

public interface ConfigService {
    void save(Config config);

    Config getConfig(long id);
}
