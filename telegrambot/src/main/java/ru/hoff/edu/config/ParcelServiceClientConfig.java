package ru.hoff.edu.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.hoff.edu.service.ParcelServiceClient;

@Configuration
@RequiredArgsConstructor
public class ParcelServiceClientConfig {

    private final ParcelServiceProperties properties;

    @Bean
    public ParcelServiceClient parcelServiceClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(properties.getParcelService())
                .build();

        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(ParcelServiceClient.class);
    }
}
