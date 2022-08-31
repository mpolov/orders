package com.polov.shop.orders.web;

import java.util.List;

import javax.transaction.Transactional;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.polov.shop.orders.model.Product;
import com.polov.shop.orders.repository.ProductRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @Operation(summary = "Fetch product list", responses = {
            @ApiResponse(responseCode = "200", description = "Result returned"),
            @ApiResponse(responseCode = "500", description = "Error processing the request") })
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @Transactional
    public List<Product> get() {
        return (List<Product>) productRepository.findAll();
    }

    @Operation(summary = "Fetch product detail", responses = {
            @ApiResponse(responseCode = "200", description = "Result returned"),
            @ApiResponse(responseCode = "500", description = "Error processing the request") })
    @GetMapping(path = "/{productId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @Transactional
    public Product get(@Parameter(description = "product identification", example = "1") @PathVariable Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @Operation(summary = "Create new product", responses = {
            @ApiResponse(responseCode = "200", description = "Product created"),
            @ApiResponse(responseCode = "500", description = "Error processing the request") })
    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @Transactional
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @Operation(summary = "Update product detail", responses = {
            @ApiResponse(responseCode = "200", description = "Product updated returned"),
            @ApiResponse(responseCode = "404", description = "Product to udpate was not found in DB"),
            @ApiResponse(responseCode = "500", description = "Error processing the request") })
    @PutMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @Transactional
    public Product update(@RequestBody Product product) {
        var productId = product.getId();
        if (productId != null && productRepository.existsById(product.getId())) {
            return productRepository.save(product);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    @Operation(summary = "Delete product", responses = {
            @ApiResponse(responseCode = "204", description = "Product removed"),
            @ApiResponse(responseCode = "500", description = "Error processing the request") })
    @DeleteMapping(path = "/{productId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@Parameter(description = "product identification", example = "1") @PathVariable Long productId) {
        productRepository.deleteById(productId);
    }

}
