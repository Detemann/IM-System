package com.sarrus.command.repositories;

import com.sarrus.command.dto.PlaylistDTO;
import com.sarrus.command.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

    @Query("SELECT p FROM Playlist p " +
            "LEFT JOIN User u ON u.id = p.userId.id " +
            "WHERE p.userId.id = ?1 AND p.id = ?2")
    Optional<Playlist> findByUserIdAndId(Integer userId, Integer playlistId);

    @Query("SELECT p FROM Playlist p " +
            "INNER JOIN User u ON u.id = p.userId.id " +
            "WHERE p.userId.id = ?1")
    List<Playlist> findAllPlaylistsByUserId(Integer userid);
}
