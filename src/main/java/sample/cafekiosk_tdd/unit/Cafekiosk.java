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

    public void remove(Beverage beverage) {
        beverages.remove(beverage);
    }

    public void clear() {
        beverages.clear();
    }

    public int calculateTotalPrice() {
        return 8500;
    }
}
