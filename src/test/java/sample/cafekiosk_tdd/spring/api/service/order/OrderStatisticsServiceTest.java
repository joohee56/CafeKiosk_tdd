package sample.cafekiosk_tdd.spring.api.service.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static sample.cafekiosk_tdd.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk_tdd.spring.domain.product.ProductType.HANDMADE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import sample.cafekiosk_tdd.spring.IntegrationTestSupport;
import sample.cafekiosk_tdd.spring.client.MailSendClient;
import sample.cafekiosk_tdd.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk_tdd.spring.domain.history.mail.MailSendHistoryRepository;
import sample.cafekiosk_tdd.spring.domain.order.Order;
import sample.cafekiosk_tdd.spring.domain.order.OrderRepository;
import sample.cafekiosk_tdd.spring.domain.order.OrderStatus;
import sample.cafekiosk_tdd.spring.domain.orderproduct.OrderProductRepository;
import sample.cafekiosk_tdd.spring.domain.product.Product;
import sample.cafekiosk_tdd.spring.domain.product.ProductRepository;
import sample.cafekiosk_tdd.spring.domain.product.ProductType;

class OrderStatisticsServiceTest extends IntegrationTestSupport {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStatisticsService orderStatisticsService;
    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @MockBean
    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @DisplayName("주어진 날짜에 해당하는 결제완료 주문을 조회하여 매출 통계 메일을 전송한다.")
    @Test
    void sendOrderStatisticsMail() {
        //given
        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 2000);
        Product product3 = createProduct(HANDMADE, "003", 3000);
        List<Product> products = List.of(product1, product2, product3);
        productRepository.saveAll(products);

        LocalDate targetDate = LocalDate.of(2024, 10, 5);

        Order order1 = createPaymentCompletedOrder(LocalDateTime.of(2024, 10, 4, 23, 59, 59), products);
        Order order2 = createPaymentCompletedOrder(LocalDateTime.of(2024,10, 5, 0, 0), products);
        Order order3 = createPaymentCompletedOrder(LocalDateTime.of(2024,10, 5, 23, 59, 59), products);
        Order order4 = createPaymentCompletedOrder(LocalDateTime.of(2024,10, 6, 0, 0), products);

        given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .willReturn(true);

        //when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(targetDate, "test@test.com");

        //then
        assertThat(result).isTrue();
        List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
        assertThat(histories).hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 12000원입니다.");
    }

    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
                .type(type)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }

    private Order createPaymentCompletedOrder(LocalDateTime dateTime, List<Product> products) {
        Order order = Order.builder()
                .products(products)
                .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                .registeredDateTime(dateTime)
                .build();
        return orderRepository.save(order);
    }

}