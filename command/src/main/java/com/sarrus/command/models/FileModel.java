package com.sarrus.command.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "files")
@Entity(name = "files")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer time;
    private String filePath;
    private String fileType;
    @ManyToOne
    private User user;
    @ManyToOne
    private Playlist playlist;
}
