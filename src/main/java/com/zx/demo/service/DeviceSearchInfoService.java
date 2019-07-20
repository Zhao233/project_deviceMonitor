package com.zx.demo.service;

import com.zx.demo.domain.DeviceSearchInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeviceSearchInfoService {
    /**管理员*/
    Page<DeviceSearchInfo> getAll(Pageable pageable);

    void addOne(DeviceSearchInfo deviceSearchInfo);

    void updateOne(DeviceSearchInfo deviceSearchInfo);

    void removeOne(Long id);


}
