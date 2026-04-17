package br.org.kinflasy.libs.media.dto;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class MediaDto {

    private UUID id;
    private String originalFilename;
    private String mimeType;
    private Long fileSize;
    private String url;

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class WithContent extends MediaDto {
        private byte[] content;
    }

}
