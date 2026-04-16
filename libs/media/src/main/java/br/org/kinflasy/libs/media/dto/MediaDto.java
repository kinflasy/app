package br.org.kinflasy.libs.media.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class MediaDto {

    private UUID id;
    private String originalFilename;
    private String mimeType;
    private Long fileSize;

}
