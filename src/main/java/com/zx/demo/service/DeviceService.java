package com.zx.demo.service;

import com.zx.demo.domain.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeviceService{
    Page<Device> getAllDevice(Pageable pageable);
    Page<Device> getAllDevice_(Pageable pageable, long userId);
    Page<Device> getAllDevice(String search, Pageable pageable);
    Page<Device> getAllDevice_(String search, Pageable pageable, long userId);

    boolean isExist(String id);

    boolean isExist_compare_newAndOld(String deviceId_new, String deviceId_old);

    void addDevice(Device device);

    Device getDeviceByDeviceId(String deviceId);

    void deleteDeviceById(long id);

    void updateDevice(Device device);

    Device getDeviceById(int id);

    long getIdByDevice_mac_id(String device_mac_id);

    String getDeviceNameById(long id);

    String getDevice_mac_idById(long id);

    List<Device> getDevicesByUserId(long userId);
}
