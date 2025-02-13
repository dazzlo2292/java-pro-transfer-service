package ru.otus.java.pro.mt.core.transfers.factories;

import lombok.RequiredArgsConstructor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.otus.java.pro.mt.core.transfers.configs.properties.RestClientProperties;

@Component
@RequiredArgsConstructor
public class RestClientFactory {

    public RestClient createRestClient(RestClientProperties restClientProperties) {
        HttpComponentsClientHttpRequestFactory requestFactorySettings = new HttpComponentsClientHttpRequestFactory();

        requestFactorySettings.setReadTimeout(restClientProperties.getReadTimeout());
        requestFactorySettings.setConnectTimeout(restClientProperties.getConnectTimeout());

        return RestClient.builder()
                .requestFactory(requestFactorySettings)
                .baseUrl(restClientProperties.getUrl())
//                .defaultUriVariables(Map.of("variable", "foo"))
//                .defaultHeader("My-Header", "Foo")
//                .requestInterceptor(myCustomInterceptor)
//                .requestInitializer(myCustomInitializer)
                .build();
    }
}
