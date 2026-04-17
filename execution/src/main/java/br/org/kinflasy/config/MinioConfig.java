package br.org.kinflasy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;
import io.minio.MinioClient.Builder;

@Configuration
public class MinioConfig {

    @Bean
    MinioClient minioClient(
            @Value("${app.minio.url}") String url,
            @Value("${app.minio.access-key}") String accessKey,
            @Value("${app.minio.secret-key}") String secretKey) {
        Builder builder = MinioClient.builder();
        builder
                .endpoint(url)
                .credentials(accessKey, secretKey);
        return builder
                .build();
    }

}
