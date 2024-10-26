package sample.cafekiosk_tdd.unit;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import sample.cafekiosk_tdd.unit.beverages.Beverage;

@Getter
public class Cafekiosk {
    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        beverages.add(beverage);
    }

    public void add(Beverage beverage, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("음료는 1잔 이상부터 주문하실 수 있습니다.");
        }
        for (int i=0; i<count; i++) {
            beverages.add(beverage);
        }
    }

    public void remove(Beverage beverage) {
        beverages.remove(beverage);
    }

    public void clear() {
        beverages.clear();
    }

    public int calculateTotalPrice() {
        return beverages.stream()
                .mapToInt(Beverage::getPrice)
                .sum();
    }
}
