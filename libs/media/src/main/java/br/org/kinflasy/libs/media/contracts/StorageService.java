package br.org.kinflasy.libs.media.contracts;

import java.io.IOException;

public interface StorageService {

    String upload(byte[] content, String fileName) throws IOException;

    byte[] download(String fileId) throws IOException;

    void delete(String fileId) throws IOException;

    String getUrl(String fileId);

}
