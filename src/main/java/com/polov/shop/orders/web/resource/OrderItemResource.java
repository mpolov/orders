package com.polov.shop.orders.web.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResource {

    @Schema(description = "product identification")
    Long id;

    @Schema(description = "product quantity")
    int quantity;

}
