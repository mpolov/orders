package com.polov.shop.orders.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.polov.shop.orders.model.Order;
import com.polov.shop.orders.model.OrderItem;
import com.polov.shop.orders.model.Product;
import com.polov.shop.orders.repository.OrderRepository;
import com.polov.shop.orders.repository.ProductRepository;
import com.polov.shop.orders.web.resource.OrderItemResource;
import com.polov.shop.orders.web.resource.OrderResource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest(classes = { OrderService.class })
@Import({ OrderServiceTest.TestConfiguration.class })
class OrderServiceTest {

    private static final String PRODUCT_NAME = "Product";
    private static final int PRODUCT_QUANTITY = 10;
    private static final float PRODUCT_PRICE = 21.5f;
    private static final int ORDER_QUANTITY = 2;

    @Configuration
    @ComponentScan(basePackages = { "com.polov.shop.orders" })
    public static class TestConfiguration {
    }

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderService orderService;

    Long productId;

    @BeforeEach
    void setup() {
        if (((List<Product>) productRepository.findAll()).isEmpty()) {
            productId = productRepository.save(Product.builder().withName(PRODUCT_NAME).withPrice(PRODUCT_PRICE)
                    .withQuantity(PRODUCT_QUANTITY).build()).getId();
        }
    }

    @Test
    void test() {
        // product exists
        assertThat(productId).isNotNull();
        // order placed
        var items = List.of(OrderItemResource.builder().id(productId).quantity(ORDER_QUANTITY).build());
        var resource = OrderResource.builder().items(items).build();
        var orderResource = orderService.save(resource);
        assertThat(orderResource).isEmpty();
        // order can be loaded
        var orders = orderService.getOrders();
        assertThat(orders).isNotEmpty().hasSize(1);
        var actualOrder = orders.get(0);
        // order looks like we expect
        var expectedOrder = Order.builder().withId(1L).withItems(List.of(OrderItem.builder().withId(1L)
                .withProductId(1L).withProductId(productId).withQuantity(ORDER_QUANTITY).build())).build();
        assertThat(actualOrder).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedOrder);

    }

}
