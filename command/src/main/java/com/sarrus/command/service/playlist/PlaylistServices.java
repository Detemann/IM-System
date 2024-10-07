package com.sarrus.command.service.playlist;

import com.sarrus.command.dto.PlaylistDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlaylistServices {
    public PlaylistDTO getPlaylist(Integer id);

    public List<PlaylistDTO> getPlaylists(Integer id);

    public void createPlaylist(PlaylistDTO playlistDTO);

    public void updatePlaylist(PlaylistDTO playlistDTO);

    public void deletePlaylist(Integer id);
}
