package com.polov.shop.orders.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Value;

@ConstructorBinding
@ConfigurationProperties("openapi")
@Value
public class OpenAPISettings {

    /**
     * Version parameter of the OpenApi specs
     */
    String version;

    /**
     * Title parameter of the OpenApi specs
     */
    String title;

    /**
     * Description parameter of the OpenApi specs
     */
    String description;

    /**
     * Contact URL parameter of the OpenApi specs
     */
    String contactUrl;

    /**
     * Contact name parameter of the OpenApi specs
     */
    String contactName;

}
