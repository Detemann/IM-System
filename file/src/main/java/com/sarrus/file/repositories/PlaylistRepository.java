package com.sarrus.file.repositories;

import com.sarrus.file.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Integer> {

    @Query("SELECT p FROM Playlist p " +
            "LEFT JOIN User u ON u.id = p.userId.id " +
            "WHERE p.userId.id = ?1 AND p.id = ?2")
    Optional<Playlist> findByUserIdAndId(Integer userId, Integer playlistId);
}
