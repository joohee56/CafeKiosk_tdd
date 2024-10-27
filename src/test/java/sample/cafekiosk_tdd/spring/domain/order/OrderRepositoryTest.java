package sample.cafekiosk_tdd.spring.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk_tdd.spring.domain.product.ProductSellingStatus.SELLING;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk_tdd.spring.IntegrationTestSupport;
import sample.cafekiosk_tdd.spring.domain.product.Product;
import sample.cafekiosk_tdd.spring.domain.product.ProductRepository;
import sample.cafekiosk_tdd.spring.domain.product.ProductSellingStatus;

@Transactional
class OrderRepositoryTest extends IntegrationTestSupport {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @DisplayName("지정된 날짜와 주문 상태에 해당하는 주문리스트를 조회한다.")
    @Test
    void findOrdersBy() {
        //given
        Product product = createProduct("001", SELLING, "아메리카노", 4000);
        productRepository.save(product);
        LocalDateTime date1 = LocalDateTime.of(2024, 10, 26, 23, 59);
        LocalDateTime date2 = LocalDateTime.of(2024, 10, 27, 00, 00);
        LocalDateTime date3 = LocalDateTime.of(2024, 10, 27, 23, 59);
        LocalDateTime date4 = LocalDateTime.of(2024, 10, 28, 00, 00);

        Order order1 = Order.create(List.of(product), date1);
        Order order2 = Order.create(List.of(product), date2);
        Order order3 = Order.create(List.of(product), date3);
        Order order4 = Order.create(List.of(product), date4);
        orderRepository.saveAll(List.of(order1, order2, order3, order4));

        LocalDateTime startDateTime = LocalDateTime.of(2024, 10, 27, 00, 00);
        LocalDateTime endDateTime = LocalDateTime.of(2024, 10, 28, 00, 00);
        OrderStatus orderStatus = OrderStatus.INIT;

        //when
        List<Order> orders = orderRepository.findOrdersBy(startDateTime, endDateTime, orderStatus);

        //then
        assertThat(orders).hasSize(2);
        assertThat(orders)
                .extracting("registeredDateTime", "orderStatus")
                .containsExactlyInAnyOrder(
                        tuple(date2, orderStatus),
                        tuple(date3, orderStatus)
                );
    }

    private Product createProduct(String productNumber, ProductSellingStatus sellingStatus, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }


}