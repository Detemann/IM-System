package com.sarrus.command.repositories;

import com.sarrus.command.models.FileModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileModel, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE files " +
            "SET playlist = NULL " +
            "WHERE playlist.id = 1")
    void deleteByPlaylistId(Integer playlistId);
}
