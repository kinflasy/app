package br.org.kinflasy.libs.media.dto;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MediaDto {

    private UUID id;
    private String originalFilename;
    private String mimeType;
    private Long fileSize;
    private String url;

    @Data
    @Accessors(chain = true)
    @EqualsAndHashCode(callSuper = true)
    public static class WithContent extends MediaDto {
        private byte[] content;
    }

}
