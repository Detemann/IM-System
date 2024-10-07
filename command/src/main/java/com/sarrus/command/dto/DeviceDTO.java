package com.sarrus.command.dto;

import com.sarrus.command.models.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO {
    private Integer deviceId;
    private String name;
    private String address;
    private Integer userId;
    private Integer playlistId;

    public DeviceDTO(Device deviceDTO) {
        this.deviceId = deviceDTO.getId();
        this.name = deviceDTO.getName();
        this.address = deviceDTO.getAddress();
        this.userId = deviceDTO.getUser().getId();
        this.playlistId = deviceDTO.getPlaylist().getId();
    }
}
