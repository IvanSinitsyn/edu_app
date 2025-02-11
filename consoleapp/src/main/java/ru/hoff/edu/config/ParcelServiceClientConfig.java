package ru.hoff.edu.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import ru.hoff.edu.service.ParcelServiceClient;

@Configuration
public class ParcelServiceClientConfig {

    @Value("${services.url.parcel-service}")
    private String parcelServiceUrl;

    @Bean
    public ParcelServiceClient parcelServiceClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(parcelServiceUrl)
                .build();

        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(ParcelServiceClient.class);
    }
}
