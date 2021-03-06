package com.zx.demo.repository.Admin;

import com.zx.demo.domain.Device;
import com.zx.demo.model.DeviceInfo_saveToDataBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface DeviceDao extends JpaRepository<Device, Long> {
    /**管理员*/
    @Query(value = "SELECT device FROM Device device where ( device.name like %?1% " +
            "             OR device.addressOfDevice LIKE %?1% OR device.attributionOfDevice LIKE %?1% OR device.level LIKE %?1%)")
    Page<Device> getAllDevice(String search, Pageable pageable);

    /**普通用户*/

    @Query(value = "select device from Device device where device.mac_id=?1")
    Device getDeviceByDeviceId(String id);

    @Query(value = "select device from Device device where device.mac_id=?1 and device.mac_id !=?2")
    Device getDeviceByDeviceId(String id_new, String id_old);

    @Query("select device from Device device where device.user_id=?2 and ( device.name like %?1% "
            + " or device.addressOfDevice like %?1% or device.attributionOfDevice like %?1% or device.level like %?1%)" )
    Page<Device> getAllDevice(String search, long userId, Pageable pageable);

    @Query("SELECT device from Device device where device.user_id=?1")
    Page<Device> getAll(Long userId, Pageable pageable);

    @Query(value = "select * from device where id=?1",nativeQuery = true)
    Device getDeivce(int id);

    @Query(value = "select device.id from Device device where device.mac_id = ?1")
    long getIdByDevice_mac_id(String device_mac_id);

    @Query(value = "select device.name from Device device where device.id = ?1")
    String getDeviceNameById(long id);

    @Query(value = "select device.mac_id from Device device where device.id = ?1")
    String getDevice_mac_id(long id);

    @Query(value = "select device from Device device where device.user_id = ?1")
    List<Device> getDevicesByUserId(long userId);

    @Query(value = "SELECT device.id FROM Device device WHERE device.cardNumber = ?1")
    Long getIdByCardNumber(String cardNumber);
}
