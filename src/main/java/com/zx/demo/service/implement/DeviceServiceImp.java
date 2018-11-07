package com.zx.demo.service.implement;

import com.zx.demo.domain.Device;
import com.zx.demo.repository.DeviceDao;
import com.zx.demo.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "deviceService")
public class DeviceServiceImp implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public Page<Device> getAllDevice(Pageable page) {
        return deviceDao.findAll(page);
    }

    @Override
    public Page<Device> getAllDevice(String search, Pageable pageable) {
        return deviceDao.getAllDevice(search, pageable);
    }


    @Override
    public boolean isExist(String deviceId) {
        Device device = getDeviceByDeviceId(deviceId);

        if(device != null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isExist_compare_newAndOld(String deviceId_new,String deviceId_old) {
        Device device = deviceDao.getDeviceByDeviceId(deviceId_new,deviceId_old);

        if(device != null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addDevice(Device device) {
        deviceDao.save(device);
    }

    @Override
    public Device getDeviceByDeviceId(String deviceId) {
        return deviceDao.getDeviceByDeviceId(deviceId);
    }

    @Override
    public void deleteDeviceById(long id) {
        deviceDao.deleteById(id);
    }

    @Override
    public void updateDevice(Device device) {
        deviceDao.save(device);
    }

    @Override
    public Device getDeviceById(int id) {
        return deviceDao.getDeivce(id);
    }

    @Override
    public long getIdByDevice_mac_id(String device_mac_id) {
        return deviceDao.getIdByDevice_mac_id(device_mac_id);
    }

    @Override
    public String getDeviceNameById(long id) {
        return deviceDao.getDeviceNameById(id);
    }

    @Override
    public String getDevice_mac_idById(long id){
        return deviceDao.getDevice_mac_id(id);
    }

    @Override
    public List<Device> getDevicesByUserId(long userId){
        return deviceDao.getDevicesByUserId(userId);
    }
}
