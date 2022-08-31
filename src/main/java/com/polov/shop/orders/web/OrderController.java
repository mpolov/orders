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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Fetch order list", responses = {
            @ApiResponse(responseCode = "200", description = "Result returned"),
            @ApiResponse(responseCode = "500", description = "Error processing the request") })
    @GetMapping
    public List<OrderResource> getOrders() {
        return orderService.getOrders();
    }

    @Operation(summary = "Place order", responses = {
            @ApiResponse(responseCode = "200", description = "Result of order placement. If order placed, nothing is returned. In case order is not placed due to missing quantity, id is null and order items are quantities of product, that are missing,"),
            @ApiResponse(responseCode = "409", description = "Product is blocked by processing different order", content = {}),
            @ApiResponse(responseCode = "500", description = "Error processing the request") })
    @PostMapping
    public Optional<OrderResource> placeOrder(@RequestBody OrderResource resource) {
        try {
            return orderService.save(resource);
        } catch (TransactionSystemException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

    }

    @Operation(summary = "Delete order", responses = {
            @ApiResponse(responseCode = "204", description = "Product removed"),
            @ApiResponse(responseCode = "500", description = "Error processing the request") })
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
