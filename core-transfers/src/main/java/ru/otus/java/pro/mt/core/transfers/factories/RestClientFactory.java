package ru.otus.java.pro.mt.core.transfers.factories;

import lombok.RequiredArgsConstructor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.otus.java.pro.mt.core.transfers.configs.properties.IntegrationRestClientProperties;

@Component
@RequiredArgsConstructor
public class RestClientFactory {

    public RestClient createRestClient(IntegrationRestClientProperties integrationProperties) {
        HttpComponentsClientHttpRequestFactory requestFactorySettings = new HttpComponentsClientHttpRequestFactory();

        requestFactorySettings.setReadTimeout(integrationProperties.getReadTimeout());
        requestFactorySettings.setConnectTimeout(integrationProperties.getConnectTimeout());

        return RestClient.builder()
                .requestFactory(requestFactorySettings)
                .baseUrl(integrationProperties.getUrl())
//                .defaultUriVariables(Map.of("variable", "foo"))
//                .defaultHeader("My-Header", "Foo")
//                .requestInterceptor(myCustomInterceptor)
//                .requestInitializer(myCustomInitializer)
                .build();
    }
}
