package com.polov.shop.orders.web.resource;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResource {
    
    Long id;
    
    List<OrderItemResource> items;

}
