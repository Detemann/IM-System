package com.sarrus.file.repositories;

import com.sarrus.file.models.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileModel, Integer> {
}
