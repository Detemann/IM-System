package com.sarrus.file.repositories;

import com.sarrus.file.models.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileModel, Integer> {

    @Query("SELECT f FROM files f " +
            "WHERE f.playlist.id = ?1 AND f.user.id = ?2")
    List<Optional<FileModel>> findByPlaylistAndUser(Integer playlistId, Integer userId);
}
