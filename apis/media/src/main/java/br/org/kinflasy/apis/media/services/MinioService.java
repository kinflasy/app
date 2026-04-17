package br.org.kinflasy.apis.media.services;

import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.org.kinflasy.libs.media.contracts.StorageService;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.Cleanup;
import lombok.SneakyThrows;

@Service
public class MinioService implements StorageService {

    private final MinioClient client;
    private final String bucketName;

    public MinioService(final MinioClient client, @Value("${app.storage.bucket}") final String bucketName) {
        this.client = client;
        this.bucketName = bucketName;
    }

    @Override
    @SneakyThrows
    public void upload(final byte[] content, final String filename) {
        @Cleanup
        final var stream = new ByteArrayInputStream(content);

        client.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .stream(stream, stream.available(), -1)
                .build());
    }

    @Override
    @SneakyThrows
    public byte[] download(final String fileId) {
        return client.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(fileId)
                .build())
                .readAllBytes();
    }

    @Override
    @SneakyThrows
    public void delete(final String fileId) {
        client.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(fileId)
                .build());
    }

    @Override
    @SneakyThrows
    public String getUrl(final String fileId) {
        return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName)
                .object(fileId)
                .method(Method.GET)
                .expiry(60, TimeUnit.SECONDS)
                .build());
    }

}
