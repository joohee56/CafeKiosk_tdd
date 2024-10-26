package sample.cafekiosk_tdd.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk_tdd.unit.beverages.Americano;
import sample.cafekiosk_tdd.unit.beverages.Latte;

class CafekioskTest {

    @DisplayName("주문 목록에 음료를 추가한다.")
    @Test
    void add() {
        //given
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();

        //when
        cafekiosk.add(americano);

        //then
        assertThat(cafekiosk.getBeverages()).hasSize(1);
        assertThat(cafekiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("주문 목록에서 음료를 삭제한다.")
    @Test
    void remove() {
        //given
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();
        cafekiosk.add(americano);
        assertThat(cafekiosk.getBeverages()).hasSize(1);

        //when
        cafekiosk.remove(americano);

        //then
        assertThat(cafekiosk.getBeverages()).isEmpty();
    }

    @DisplayName("주문 목록을 전체 삭제한다.")
    @Test
    void clear() {
        //given
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();
        cafekiosk.add(americano);
        cafekiosk.add(latte);
        assertThat(cafekiosk.getBeverages()).hasSize(2);

        //when
        cafekiosk.clear();

        //then
        assertThat(cafekiosk.getBeverages()).isEmpty();
    }

    @DisplayName("주문 목록에서 총 금액을 조회한다.")
    @Test
    void calculateTotalPrice() {
        //given
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();
        cafekiosk.add(americano);
        cafekiosk.add(latte);

        //when
        int totalPrice = cafekiosk.calculateTotalPrice();

        //then
        assertThat(totalPrice).isEqualTo(8500);
    }

    @DisplayName("한 음료를 여러 잔 추가한다.")
    @Test
    void addSeveralBeverages() {
        //given
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();

        //when
        cafekiosk.add(americano, 2);

        // then
        assertThat(cafekiosk.getBeverages()).hasSize(2);
        assertThat(cafekiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
        assertThat(cafekiosk.getBeverages().get(1).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("음료를 0잔 추가하면 예외가 발생한다.")
    @Test
    void addZeroBeverages() {
        //given
        Cafekiosk cafekiosk = new Cafekiosk();
        Americano americano = new Americano();
        //when, then
        assertThatThrownBy(() -> cafekiosk.add(americano, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상부터 주문하실 수 있습니다.");
    }

}