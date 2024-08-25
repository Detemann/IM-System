package com.sarrus.file.models;

import com.sarrus.file.dtos.FileDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Table(name = "files")
@Entity(name = "file")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String filePath;
    private String fileType;
    @ManyToOne
    private User user;
    @ManyToOne
    private Playlist playlist;
    @Transient
    private MultipartFile multipartFile;

    public FileModel(MultipartFile file, Path filePath) {
        this.name = file.getOriginalFilename();
        this.fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        this.multipartFile = file;
        //Todos os arquivos terão o caminho no seu formado .zip porque apenas arquivos zipados serão salvos
        this.filePath = filePath.resolve(file.getOriginalFilename()).toString().replaceAll("\\.(png|jpg|mp4)", ".zip");
    }

    public FileModel(MultipartFile file) {
        this.name = file.getOriginalFilename();
        this.fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        this.multipartFile = file;
    }
}
