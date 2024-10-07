package com.sarrus.command.models;

import com.sarrus.command.dto.DeviceDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "devices")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    @ManyToOne
    private User user;
    @ManyToOne
    private Playlist playlist;

    public Device(DeviceDTO device) {
        this.name = device.getName();
        this.address = device.getAddress();
    }
}
