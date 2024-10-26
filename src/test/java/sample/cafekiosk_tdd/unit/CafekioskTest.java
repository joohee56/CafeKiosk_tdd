package sample.cafekiosk_tdd.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk_tdd.unit.beverages.Americano;

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

}