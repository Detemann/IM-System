package com.sarrus.api_files.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseFileDTO {
        Integer fileId;
        Integer playlistId;
        Integer time;
        FileDTO file;

        public ResponseFileDTO(Integer fileId, String fileName, Integer playlistId, Integer time, byte[] fileContent) {
                this.fileId = fileId;
                this.playlistId = playlistId;
                this.time = time;
                this.file = new FileDTO(fileName, fileContent);
        }
}
