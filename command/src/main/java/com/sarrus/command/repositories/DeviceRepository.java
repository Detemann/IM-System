package com.sarrus.command.repositories;

import com.sarrus.command.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    @Query("SELECT d FROM Device d " +
            "WHERE d.user.id = ?1")
    List<Device> allDevicesByUserId(int userId);
}
