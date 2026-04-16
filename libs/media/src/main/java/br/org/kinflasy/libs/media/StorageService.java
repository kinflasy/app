package br.org.kinflasy.libs.media;

import java.io.IOException;

public interface StorageService {

    String uploadImage(byte[] content, String fileName) throws IOException;

    byte[] downloadImage(String fileId) throws IOException;

    void deleteImage(String fileId) throws IOException;

    String getImageUrl(String fileId);

}
