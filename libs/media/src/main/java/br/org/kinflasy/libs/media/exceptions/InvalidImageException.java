package br.org.kinflasy.libs.media.exceptions;

/**
 * Exceção lançada quando um arquivo de imagem é inválido.
 * Pode ser por tipo MIME inválido, tamanho excedido, ou outro critério.
 */
public class InvalidImageException extends RuntimeException {

    public InvalidImageException(final String message) {
        super(message);
    }

    public InvalidImageException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
