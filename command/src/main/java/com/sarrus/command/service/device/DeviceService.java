package com.sarrus.command.service.device;

import com.sarrus.command.dto.DeviceDTO;
import org.springframework.stereotype.Service;

@Service
public interface DeviceService {

    public DeviceDTO getDevice(Integer id);

    public void insertDevice(DeviceDTO device);

    public void updateDevice(DeviceDTO device);
}
