package com.sarrus.command.service.device;

import com.sarrus.command.dto.DeviceDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceService {

    public DeviceDTO getDevice(Integer id);

    public List<DeviceDTO> getAllDevices(Integer id);

    public void insertDevice(DeviceDTO device);

    public void updateDevice(DeviceDTO device);

    public void deleteDevice(Integer id);
}
