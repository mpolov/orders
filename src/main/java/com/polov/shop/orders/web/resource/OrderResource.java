package com.polov.shop.orders.web.resource;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResource {
    
    @Schema(description = "order identification")
    Long id;
    
    @Schema(description = "order items")
    List<OrderItemResource> items;

}
