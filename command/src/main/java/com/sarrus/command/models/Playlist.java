package com.sarrus.command.models;

import com.sarrus.command.dto.PlaylistDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "playlists")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    private User userId;

    public Playlist(PlaylistDTO playlistDTO) {
        this.id = playlistDTO.getPlaylistId();
        this.name = playlistDTO.getPlaylistName();
    }
}
