package com.zx.demo.service.implement;

import com.zx.demo.domain.DeviceSearchInfo;
import com.zx.demo.repository.DeviceSearchInfoDao;
import com.zx.demo.service.DeviceSearchInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class DeviceSearchInfoImp implements DeviceSearchInfoService {
    @Autowired
    private DeviceSearchInfoDao deviceSearchInfoDao;

    @Override
    public Page<DeviceSearchInfo> getAll(Pageable pageable) {
        return deviceSearchInfoDao.getAll(pageable);
    }

    @Override
    public void addOne(DeviceSearchInfo deviceSearchInfo) {
        deviceSearchInfoDao.save(deviceSearchInfo);
    }

    @Override
    public void updateOne(DeviceSearchInfo deviceSearchInfo) {

    }

    @Override
    public void removeOne(Long id) {

    }
}
