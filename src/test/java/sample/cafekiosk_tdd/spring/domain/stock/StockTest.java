package sample.cafekiosk_tdd.spring.domain.stock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StockTest {

    @DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다.")
    @Test
    void isQuantityLessThan() {
        //given
        Stock stock = Stock.create("001", 2);
        int quantity = 1;

        //when, then
        assertThat(stock.isQuantityLessThan(quantity)).isFalse();
    }

    @DisplayName("재고의 수량을 주어진 갯수만큼 차감한다.")
    @Test
    void deductQuantity() {
        //given
        Stock stock = Stock.create("001", 2);
        int quantity = 1;

        //when
        stock.deductQuantity(quantity);

        //then
        assertThat(stock.getQuantity()).isEqualTo(1);
    }

    @DisplayName("재고보다 많은 수량으로 차감할 경우 예외가 발생한다.")
    @Test
    void deductQuantity2() {
        //given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        //when, then
        assertThatThrownBy(() -> stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("차감할 재고 수량이 없습니다.");
    }

}