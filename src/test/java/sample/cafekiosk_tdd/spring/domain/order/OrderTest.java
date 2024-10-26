package sample.cafekiosk_tdd.spring.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk_tdd.spring.domain.order.OrderStatus.INIT;
import static sample.cafekiosk_tdd.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk_tdd.spring.domain.product.ProductType.HANDMADE;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk_tdd.spring.domain.product.Product;

class OrderTest {
    @DisplayName("주문 생성 시 주문 상태는 INIT 이다.")
    @Test
    void init() {
        //given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        //when
        Order order = Order.create(products, LocalDateTime.now());

        //then
        assertThat(order.getOrderStatus()).isEqualTo(INIT);
    }

    @DisplayName("주문 생성 시 상품 목록의 총 주문 금액을 계산한다.")
    @Test
    void calculateTotalPrice() {
        //given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );

        //when
        Order order = Order.create(products, LocalDateTime.now());

        //then
        assertThat(order.getTotalPrice()).isEqualTo(3000);
    }

    @DisplayName("주문 생성 시 생성 시간을 기록한다.")
    @Test
    void registeredDateTime() {
        //given
        List<Product> products = List.of(
                createProduct("001", 1000),
                createProduct("002", 2000)
        );
        LocalDateTime registeredDateTime = LocalDateTime.now();

        //when
        Order order = Order.create(products, registeredDateTime);

        //then
        assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .price(price)
                .build();
    }
}