package sample.cafekiosk_tdd.spring.api.controller.order;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk_tdd.spring.api.apiResponse.ApiResponse;
import sample.cafekiosk_tdd.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk_tdd.spring.api.service.order.OrderService;
import sample.cafekiosk_tdd.spring.api.service.order.response.OrderResponse;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/api/v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        OrderResponse order = orderService.createOrder(request, LocalDateTime.now());
        return ApiResponse.ok(order);
    }
}
