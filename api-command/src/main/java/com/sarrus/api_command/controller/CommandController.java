package com.sarrus.api_command.controller;

import com.sarrus.file.services.file.FileModelLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CommandController {

    @Autowired
    private FileModelLogic fileModelLogic;

    @GetMapping("/unzipAndSendFiles")
    public ResponseEntity<Map<String, byte[]>> unzipAndSendFiles(@RequestParam Integer userId, @RequestParam Integer fileId) {
        try {
            Map<String, byte[]> unzippedFiles = fileModelLogic.unzipFiles(userId, fileId);
            return ResponseEntity.ok(unzippedFiles);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
