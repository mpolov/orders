package com.polov.shop.orders.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

/**
 * The configuration of the generated Open API documentation.
 */
@Configuration
@EnableConfigurationProperties({OpenAPISettings.class})
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI(OpenAPISettings settings) {
        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(new Info()
                        .title(settings.getTitle())
                        .version(settings.getVersion())
                        .description(settings.getDescription())
                        .contact(new Contact()
                                .url(settings.getContactUrl())
                                .name(settings.getContactName())));
    }
}