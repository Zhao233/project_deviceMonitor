package com.zx.demo.repository;//package com.zx.demo.repository;
//
//import com.zx.demo.domain.DeviceInfo;
//import com.zx.demo.model.DeviceInfo_all;
//
//import com.zx.demo.model.DeviceInfo_detail;
//import com.zx.demo.model.DeviceInfo_saveToDataBase;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import com.zx.demo.domain.DeviceInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceInfoDao extends JpaRepository<DeviceInfo, Long> {
////    @Query(value = "select new com.zx.demo.model.DeviceInfo_all(" +
////            "device.id,             device.mac_id,            device.type,              " +
////            "device.level,              device.name,              deviceInfo.statusOfLight, " +
////            "deviceInfo.statusOfCharge, deviceInfo.temperature,   deviceInfo.humidity," +
////            "max (deviceInfo.updateTime), device.imei_id) " +
////
////            "from DeviceInfo deviceInfo " +
////            "inner join Device device " +
////            "on deviceInfo.device_mac_id = device.mac_id " +
////            "group by deviceInfo.device_mac_id ")
////    Page<DeviceInfo_all> getAll_latest(Pageable pageable);
//
//
////    @Query(value = "select * from device_info " +
////            "where device_info.id = ?1 order by device_info.update_time desc " +
////            "limit 1", nativeQuery = true)
//    DeviceInfo getDeviceInfoById_latest(long id);
//
//
//    /**
//     *get latest deviceInfo_detail record by it's device id
//     * */
////    @Query(value = "select new com.zx.demo.model.DeviceInfo_detail( deviceInfo.id, device.mac_id,  "      +
////            "device.name,               device.attributionOfDevice, device.organizationId, "      +
////            "device.addressOfDevice,    deviceInfo.times,           deviceInfo.signalIntensity, " +
////            "deviceInfo.statusOfCharge, deviceInfo.statusOfLight,   deviceInfo.temperature, "     +
////            "deviceInfo.humidity,       deviceInfo.updateTime, device.imei_id ) " +
////
////            "from DeviceInfo deviceInfo " +
////            "inner join Device device " +
////            "on deviceInfo.device_mac_id = device.mac_id " +
////            "where device.id = ?1 order by deviceInfo.updateTime desc"+
////            "")
//    Page<DeviceInfo_detail> gerDeviceInfoDetail(long id, Pageable pageable);
//
//
//    /**
//     * get organization name by it's id
//     * */
////    @Query(value = "select organization.name from Organization organization where id = ?1")
//    String getOrganizationNameById(long id);
//
//    /**
//     * get 12 recently temperature record by device id and selected datetime
//     * */
////    @Query(value = " select temperature " +
////            "from device_info " +
////            "where device_info.device_id = ?1 " +
////            "and unix_timestamp(device_info.update_time) >= unix_timestamp(?2) " +
////            "order by device_info.update_time asc limit 24", nativeQuery = true)
//    List<String> getTemperatureRecord(long id,String dateTime);
//
////    @Query(value = " select device_info.update_time " +
////            "from device_info " +
////            "where device_info.device_id = ?1 " +
////            "and unix_timestamp(device_info.update_time) >= unix_timestamp(?2) " +
////            "order by device_info.update_time asc limit 24", nativeQuery = true)
//    List<String> getUpdateTimeRecord(long id,String dateTime);
//

    @Query(value = " select humidity " +
            "from device_info " +
            "where device_info.device_id = ?1 " +
            "and unix_timestamp(device_info.update_time) >= unix_timestamp(?2) " +
            "order by device_info.update_time asc limit 24", nativeQuery = true)
    List<String> getHumidityRecord(long id,String dateTime);

    @Query(value = "select deviceInfo from DeviceInfo deviceInfo where deviceInfo.device_id in " +
            "(" +
               "select device.id from Device device where device.user_id = ?1" +
            ") order by deviceInfo.updateTime" )
    Page<DeviceInfo> getLatestDeviceInfo(long userId, Pageable pageable);
//
////    @Query(value = "select count(deviceInfo) from DeviceInfo deviceInfo where device_mac_id=?1 ")
//    int getRecordTimesByDeviceMacId(String device_mac_id);
//
////    @Query(value = "select new com.zx.demo.model.DeviceInfo_saveToDataBase(device.mac_id,device.id) from Device device")
////    List<DeviceInfo_saveToDataBase> getAllDeviceMacIdAndDeviceId();
}
