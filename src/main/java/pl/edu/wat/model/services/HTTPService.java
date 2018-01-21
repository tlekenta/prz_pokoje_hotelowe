package pl.edu.wat.model.services;

import com.google.gson.Gson;
import pl.edu.wat.model.entities.CurrencyPrice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class HTTPService {
    private static final String PRICE_USD_URL = "http://api.nbp.pl/api/exchangerates/rates/A/USD?format=json";

    public static double getDollarPrice() throws IOException {
        URL url = new URL(PRICE_USD_URL);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        BufferedReader bufferedReader = new BufferedReader(reader);

        String response = bufferedReader.lines().collect(Collectors.joining());
        Gson g = new Gson();

        CurrencyPrice currencyPrice = g.fromJson(response, CurrencyPrice.class);

        return currencyPrice.getPrice();
    }


}
