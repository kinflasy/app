package br.org.kinflasy.apis.media.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.org.kinflasy.libs.media.contracts.StorageService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.Cleanup;
import lombok.SneakyThrows;

@Service
public class MinioService implements StorageService {

    private final MinioClient client;
    private final String bucketName;

    public MinioService(final MinioClient client, @Value("${app.minio.bucket}") final String bucketName) {
        this.client = client;
        this.bucketName = bucketName;
    }

    @Override
    @SneakyThrows
    public String upload(final byte[] content, final String fileName) {
        @Cleanup
        final var stream = new ByteArrayInputStream(content);

        client.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .stream(stream, stream.available(), -1)
                .build());

        return null;
    }

    @Override
    public byte[] download(final String fileId) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'download'");
    }

    @Override
    public void delete(final String fileId) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public String getUrl(final String fileId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUrl'");
    }

}
