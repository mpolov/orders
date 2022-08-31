package com.polov.shop.orders.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import com.polov.shop.orders.model.Order;
import com.polov.shop.orders.model.OrderItem;
import com.polov.shop.orders.repository.OrderRepository;
import com.polov.shop.orders.repository.ProductRepository;
import com.polov.shop.orders.web.mapper.OrderMapper;
import com.polov.shop.orders.web.resource.OrderItemResource;
import com.polov.shop.orders.web.resource.OrderResource;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Transactional
    public Optional<OrderResource> save(OrderResource resource) {

        List<OrderItemResource> items = resource.getItems();

        if (items == null || items.isEmpty()) {
            return Optional.empty();
        }

        List<OrderItemResource> missingItems = new ArrayList<>();
        items.stream().forEach(item -> checkItem(item, missingItems));

        if (!missingItems.isEmpty()) {
            return Optional.of(OrderResource.builder().items(missingItems).build());
        }

        var order = new Order();
        var orderItems = items.stream().map(it -> OrderItem.builder().withProductId(it.getId())
                .withQuantity(it.getQuantity()).build()).toList();
        order.setItems(orderItems);
        orderRepository.save(order);

        return Optional.empty();

    }

    @Transactional
    public void deleteOrder(Long id) {
        var order = orderRepository.findById(id).orElseThrow();
        order.getItems().stream().forEach(orderItem -> productRepository.findById(orderItem.getProductId()).ifPresent(p -> {
            var productQuantity = p.getQuantity();
            var orderQuantity = orderItem.getQuantity();
            p.setQuantity(productQuantity + orderQuantity);
            productRepository.save(p);
        }));
        orderRepository.deleteById(id);
    }

    public List<OrderResource> getOrders() {
        List<Order> orders = ((List<Order>) orderRepository.findAll());
        return orders.stream().map(orderMapper::toOrderResource).toList();
    }

    private void checkItem(OrderItemResource orderItem, List<OrderItemResource> missingItems) {

        productRepository.findById(orderItem.getId()).ifPresent(p -> {
            var productQuantity = p.getQuantity();
            var orderQuantity = orderItem.getQuantity();
            if (productQuantity < orderQuantity) {
                missingItems.add(OrderItemResource.builder().id(orderItem.getId())
                        .quantity(orderQuantity - productQuantity).build());
            } else {
                p.setQuantity(productQuantity - orderQuantity);
                productRepository.save(p);
            }
        });

    }

}
