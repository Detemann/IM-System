package com.sarrus.command.service.device;

import com.sarrus.command.dto.DeviceDTO;
import com.sarrus.command.exceptions.DataNotFoundException;
import com.sarrus.command.exceptions.DeviceLogicException;
import com.sarrus.command.models.Device;
import com.sarrus.command.models.Playlist;
import com.sarrus.command.models.User;
import com.sarrus.command.repositories.DeviceRepository;
import com.sarrus.command.repositories.PlaylistRepository;
import com.sarrus.command.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DeviceLogic implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private WebClient webClient;

    @Override
    public DeviceDTO getDevice(Integer id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Dispositivo não encontrado", id));
        return new DeviceDTO(device);
    }

    @Override
    public List<DeviceDTO> getAllDevices(Integer id) {
        try {
            return deviceRepository.allDevicesByUserId(id).stream().map(DeviceDTO::new).toList();
        } catch (Exception e) {
            throw new DeviceLogicException("[DeviceLogic -> getAllDevices] "+ e.getMessage());
        }
    }

    @Override
    public void insertDevice(@RequestBody @Valid DeviceDTO device) {
        try {
            User user = userRepository.findById(device.getUserId())
                    .orElseThrow(() -> new DataNotFoundException(device.getDeviceId(), "Usuário não encontrado"));

            Device newDevice = new Device(device);
            newDevice.setUser(user);

            deviceRepository.save(newDevice);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DeviceLogicException("[DeviceLogic -> insertDevice] " + e.getMessage());
        }
    }

    @Override
    public void updateDevice(DeviceDTO deviceDTO) {
        try {
            Device device = deviceRepository.findById(deviceDTO.getDeviceId())
                    .orElseThrow(() -> new DataNotFoundException("Dispositivo não encontrado", deviceDTO.getDeviceId()));

            Playlist playlist;
            if(deviceDTO.getPlaylistId() != null) {
                playlist = playlistRepository.findById(deviceDTO.getPlaylistId())
                        .orElseThrow(() -> new DataNotFoundException("Playlist não encontrada", deviceDTO.getPlaylistId()));

                device.setPlaylist(playlist);
            }
            if(deviceDTO.getName() != null && !deviceDTO.getName().isEmpty()) {
                device.setName(deviceDTO.getName());
            }
            if(deviceDTO.getAddress() != null && !deviceDTO.getAddress().isEmpty()) {
                device.setAddress(deviceDTO.getAddress());
            }

            deviceRepository.save(device);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DeviceLogicException("[DeviceLogic -> updateDevice] " + e.getMessage());
        }
    }

    @Override
    public void updatePlaylistsInDevices() {
        try {
             List<Device> devices = deviceRepository.findAll();

             for (Device device : devices) {
                 Map<String, Object> body = new HashMap<>();
                 body.put("playlistId", device.getPlaylist().getId());

                 webClient.put()
                         .uri("http://"+device.getAddress()+"8080/playlist")
                         .bodyValue(body)
                         .retrieve()
                         .toBodilessEntity();
             }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DeviceLogicException("[DeviceLogic -> updatePlaylistsInDevices] " + e.getMessage());
        }
    }

    @Override
    public void deleteDevice(Integer id) {
        try {
            deviceRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DeviceLogicException("[DeviceLogic -> deleteDevice] " + e.getMessage());
        }
    }


}
