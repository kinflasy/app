package br.org.kinflasy.libs.api_utils.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate template) {
        // Recupera os atributos da requisição atual da thread
        final var attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            final var request = attributes.getRequest();
            final var authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

            if (authorizationHeader != null) {
                // Injeta o token na chamada de saída do Feign
                template.header(AUTHORIZATION_HEADER, authorizationHeader);
            }
        }
    }

}
