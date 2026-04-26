package br.org.kinflasy.libs.media.validators;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import br.org.kinflasy.libs.media.exceptions.InvalidImageException;

/**
 * Validador para arquivos de imagem.
 * 
 * Valida:
 * - Tipo MIME (apenas imagens permitidas)
 * - Tamanho máximo
 * - Arquivo não vazio
 */
public interface ProfileImageValidator {

    // Tipos MIME permitidos
    static final Set<String> ALLOWED_MIME_TYPES = Set.of(
            "image/jpeg",
            "image/png");

    // Tamanho máximo em bytes (2 MB)
    static final long MAX_SIZE_BYTES = 2 * 1024 * 1024L;

    /**
     * Valida um arquivo de imagem.
     * 
     * @param file arquivo a ser validado
     * @throws InvalidImageException se o arquivo for inválido
     */
    public static void validate(final MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidImageException("Arquivo de imagem não pode estar vazio.");
        }

        validateMimeType(file.getContentType());
        validateSize(file.getSize());
    }

    /**
     * Valida o tipo MIME do arquivo.
     * 
     * @param mimeType tipo MIME do arquivo
     * @throws InvalidImageException se o tipo MIME não for permitido
     */
    private static void validateMimeType(final String mimeType) {
        if (mimeType == null || !ALLOWED_MIME_TYPES.contains(mimeType)) {
            throw new InvalidImageException(
                    "Tipo de arquivo não permitido. Apenas JPG e PNG são aceitos. Recebido: " + mimeType);
        }
    }

    /**
     * Valida o tamanho do arquivo.
     * 
     * @param fileSize tamanho do arquivo em bytes
     * @throws InvalidImageException se o tamanho exceder o máximo permitido
     */
    private static void validateSize(final long fileSize) {
        if (fileSize > MAX_SIZE_BYTES) {
            final var maxSizeMb = MAX_SIZE_BYTES / (1024 * 1024);
            final var fileSizeMb = fileSize / (1024 * 1024);
            throw new InvalidImageException(
                    "Arquivo excede o tamanho máximo permitido. Máximo: " + maxSizeMb + "MB, Recebido: " + fileSizeMb
                            + "MB");
        }
    }

}
