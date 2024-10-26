package sample.cafekiosk_tdd.unit.beverages;

import lombok.Getter;

@Getter
public class Latte implements Beverage {
    @Override
    public String getName() {
        return "라떼";
    }

    @Override
    public int getPrice() {
        return 4500;
    }
}
