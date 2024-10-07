package com.sarrus.command.controllers;

import com.sarrus.command.dto.PlaylistDTO;
import com.sarrus.command.service.playlist.PlaylistServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/command")
public class PlaylistController {

    @Autowired
    private PlaylistServices playlistServices;

    @GetMapping("/{playlistId}")
    public ResponseEntity<PlaylistDTO> getPlaylist(@PathVariable @Valid Integer playlistId) {
        PlaylistDTO playlist = playlistServices.getPlaylist(playlistId);
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<PlaylistDTO>> getAllPlaylistByUser(@PathVariable @Valid Integer userId) {
        List<PlaylistDTO> playlist = playlistServices.getPlaylists(userId);
        return ResponseEntity.ok(playlist);
    }

    @PostMapping
    public ResponseEntity<String> createPlaylist(@RequestBody @Valid PlaylistDTO playlistDTO) {
        playlistServices.createPlaylist(playlistDTO);
        return ResponseEntity.ok("Playlist criada com sucesso");
    }

    @PutMapping
    public ResponseEntity<String> updatePlaylist(@RequestBody @Valid PlaylistDTO playlistDTO) {
        playlistServices.updatePlaylist(playlistDTO);
        return ResponseEntity.ok("Playlist atualizada com sucesso");
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<String> deletePlaylist(@PathVariable @Valid Integer playlistId) {
        playlistServices.deletePlaylist(playlistId);
        return ResponseEntity.ok("Playlist deletado com sucesso");
    }
}
