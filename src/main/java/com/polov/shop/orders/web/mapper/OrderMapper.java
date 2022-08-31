package com.polov.shop.orders.web.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.polov.shop.orders.model.Order;
import com.polov.shop.orders.model.OrderItem;
import com.polov.shop.orders.web.resource.OrderItemResource;
import com.polov.shop.orders.web.resource.OrderResource;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    @Mapping(target = "id", source = "productId")
    OrderItemResource toOrderItemResource(OrderItem product);
    
    OrderResource toOrderResource(Order order);

}
