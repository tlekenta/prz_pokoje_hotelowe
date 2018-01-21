package pl.edu.wat.model.entities;

import lombok.Data;

@Data
public class CurrencyPrice {
    String table;

    String currency;

    String code;

    Rate[] rates;

    @Data
    private class Rate {
        String no;

        String effectiveDate;

        double mid;
    }

    public double getPrice() {
        return rates[0].getMid();
    }
}
