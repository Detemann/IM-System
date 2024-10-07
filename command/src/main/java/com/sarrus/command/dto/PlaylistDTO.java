package com.sarrus.command.dto;

import com.sarrus.command.models.Playlist;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDTO {
        @NotNull
        Integer userId;
        Integer playlistId;
        String playlistName;

        public PlaylistDTO(Playlist playlist) {
                this.userId = playlist.getUserId().getId();
                this.playlistId = playlist.getId();
                this.playlistName = playlist.getName();
        }
}
