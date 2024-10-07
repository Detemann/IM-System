package com.sarrus.command.controllers;

import com.sarrus.command.dto.DeviceDTO;
import com.sarrus.command.service.device.DeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping("/{deviceId}")
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable @Valid Integer deviceId) {
        DeviceDTO device = deviceService.getDevice(deviceId);
        return ResponseEntity.ok().body(device);
    }

    @PostMapping
    public ResponseEntity<String> createDevice(@RequestBody @Valid DeviceDTO device) {
        deviceService.insertDevice(device);
        return ResponseEntity.ok().body("Dispositivo criado com sucesso!");
    }

    @PutMapping
    public ResponseEntity<String> updateDevice(@RequestBody @Valid DeviceDTO device) {
        deviceService.updateDevice(device);
        return ResponseEntity.ok().body("Atualizado com sucesso!");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDevice(@RequestBody @Valid DeviceDTO device) {
        return null;
    }
}