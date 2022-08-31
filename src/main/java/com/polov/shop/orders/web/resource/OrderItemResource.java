package com.polov.shop.orders.web.resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResource {

    Long id;

    int quantity;

}
