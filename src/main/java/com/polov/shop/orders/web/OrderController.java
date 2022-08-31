package com.polov.shop.orders.web;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.polov.shop.orders.service.OrderService;
import com.polov.shop.orders.web.resource.OrderResource;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Fetch order list")
    @GetMapping
    public List<OrderResource> getOrders() {
        return orderService.getOrders();
    }

    @Operation(summary = "Place order")
    @PostMapping
    public Optional<OrderResource> placeOrder(@RequestBody OrderResource resource) {
        try {
            return orderService.save(resource);
        } catch (TransactionSystemException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

    }

    @Operation(summary = "Delete order")
    @DeleteMapping(path = "/{orderId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long orderId) {
        try {
            orderService.deleteOrder(orderId);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

}
