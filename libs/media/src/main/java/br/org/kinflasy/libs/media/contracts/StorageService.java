package br.org.kinflasy.libs.media.contracts;

import java.io.IOException;

public interface StorageService {

    void upload(byte[] content, String filename) throws IOException;

    byte[] download(String fileId) throws IOException;

    void delete(String fileId) throws IOException;

    String getUrl(String fileId);

}
