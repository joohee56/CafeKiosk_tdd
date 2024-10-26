package sample.cafekiosk_tdd.spring.api.service.order.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk_tdd.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk_tdd.spring.domain.order.Order;
import sample.cafekiosk_tdd.spring.domain.order.OrderStatus;

@Getter
public class OrderResponse {
    private Long id;
    private OrderStatus orderStatus;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductResponse> products;

    @Builder
    private OrderResponse(Long id, OrderStatus orderStatus, int totalPrice, LocalDateTime registeredDateTime, List<ProductResponse> products) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.registeredDateTime = registeredDateTime;
        this.products = products;
    }

    public static OrderResponse of(Order savedOrder) {
        return OrderResponse.builder()
                .id(savedOrder.getId())
                .orderStatus(savedOrder.getOrderStatus())
                .totalPrice(savedOrder.getTotalPrice())
                .registeredDateTime(savedOrder.getRegisteredDateTime())
                .products(savedOrder.getOrderProducts().stream()
                        .map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
                        .collect(Collectors.toList())
                )
                .build();
    }
}
