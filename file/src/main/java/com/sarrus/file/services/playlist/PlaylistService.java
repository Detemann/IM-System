package com.sarrus.file.services.playlist;

import com.sarrus.file.models.Playlist;
import com.sarrus.file.repositories.PlaylistRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService {

    private PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public void save(Playlist playlist) {
        playlistRepository.save(playlist);
    }
}
