package sample.cafekiosk_tdd.spring.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk_tdd.spring.domain.product.ProductSellingStatus.HOLD;
import static sample.cafekiosk_tdd.spring.domain.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk_tdd.spring.domain.product.ProductSellingStatus.STOP_SELLING;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매 상태의 상품 리스트를 조회한다.")
    @Test
    void findAllBySellingStatusIn() {
        //given
        Product product1 = createProduct("001", SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", STOP_SELLING, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        //when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

        //then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
                );
    }

    @DisplayName("상품 번호 리스트로 상품 목록을 조회한다.")
    @Test
    void findAllByProductNumberIn() {
        //given
        Product product1 = createProduct("001", SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", HOLD, "카페라떼", 4500);
        Product product3 = createProduct("003", STOP_SELLING, "팥빙수", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));
        List<String> productNumbers = List.of("001", "002");

        //when
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        //then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", SELLING),
                        tuple("002", "카페라떼", HOLD)
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