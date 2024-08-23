package com.sarrus.file.models;

import com.sarrus.file.dtos.FileDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "files")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String filePath;
    private String fileType;
    @ManyToOne
    private User user;
    @ManyToOne
    private Playlist playlist;

    public File(FileDTO fileDTO) {
        this.name = fileDTO.file().getName();
        this.fileType = fileDTO.file().getOriginalFilename().substring(fileDTO.file().getOriginalFilename().lastIndexOf("."));
    }
}
