package com.sarrus.file.repositories;

import com.sarrus.file.models.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileModel, Integer> {

    @Query("SELECT f FROM files f " +
            "WHERE f.playlist.id = ?1 AND f.user.id = ?2")
    List<FileModel> findByPlaylistAndUser(Integer playlistId, Integer userId);

    @Query("SELECT f FROM files f " +
            "WHERE f.user.id = ?1")
    List<FileModel> getAllFiles(Integer userId);
}
