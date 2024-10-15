package com.sarrus.command.service.playlist;

import com.sarrus.command.dto.PlaylistDTO;
import com.sarrus.command.exceptions.DataNotFoundException;
import com.sarrus.command.exceptions.PlaylistLogicException;
import com.sarrus.command.models.Playlist;
import com.sarrus.command.repositories.DeviceRepository;
import com.sarrus.command.repositories.FileRepository;
import com.sarrus.command.repositories.PlaylistRepository;
import com.sarrus.command.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlaylistLogic implements PlaylistServices {

    @Autowired
    PlaylistRepository playlistRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private FileRepository fileRepository;

    @Override
    public PlaylistDTO getPlaylist(Integer id) {
        return new PlaylistDTO(playlistRepository.findById(id).orElseThrow(DataNotFoundException::new));
    }

    public List<PlaylistDTO> getPlaylists(Integer userid) {
        return playlistRepository.findAllPlaylistsByUserId(userid).stream().map(PlaylistDTO::new).toList();
    }

    @Override
    public void createPlaylist(PlaylistDTO playlistDTO) {
        try {
            Playlist playlist = new Playlist(playlistDTO);

            playlist.setUserId(
                    userRepository.findById(
                            playlistDTO.getUserId()).orElseThrow(
                                    () -> new DataNotFoundException(playlistDTO.getUserId(), "Usuário não encontrado")));

            playlistRepository.save(playlist);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PlaylistLogicException("[PlaylistLogic -> updatePlaylist] " + e.getMessage());
        }
    }

    @Override
    public void updatePlaylist(PlaylistDTO playlistDTO) {
        try {
            Playlist playlist = playlistRepository.findById(
                    playlistDTO.getPlaylistId()).orElseThrow(
                    () -> new DataNotFoundException("Playlist não encontrada", playlistDTO.getPlaylistId()));

            playlist.setName(playlist.getName().isEmpty() ? playlist.getName() : playlistDTO.getPlaylistName());
            playlistRepository.save(playlist);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PlaylistLogicException("[PlaylistLogic -> updatePlaylist] " + e.getMessage());
        }
    }

    @Override
    public void deletePlaylist(Integer id) {
        try {
            deviceRepository.deleteByPlaylistId(id);
            fileRepository.deleteByPlaylistId(id);
            playlistRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PlaylistLogicException("[PlaylistLogic -> deletePlaylist] " + e.getMessage());
        }
    }
}
