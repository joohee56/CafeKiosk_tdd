package sample.cafekiosk_tdd.spring.api.service.order.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import sample.cafekiosk_tdd.spring.api.service.product.response.ProductResponse;
import sample.cafekiosk_tdd.spring.domain.order.OrderStatus;

@Getter
public class OrderResponse {
    private Long id;
    private OrderStatus orderStatus;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductResponse> products;
}
