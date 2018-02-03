package pl.edu.wat.web;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import pl.edu.wat.exceptions.NoSuchCurrencyException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

public class CurrencyService {
    private static Logger logger = Logger.getLogger(CurrencyService.class);
    private static final String PRICE_USD_URL = "http://api.nbp.pl/api/exchangerates/rates/A/USD?format=json";
    private static double dollarPrice = 0.0;

    public static double getCurrnecyPrice(String currnecy) {
        switch (currnecy) {
            case "PLN":
                return 1.0;
            case "USD":
                return getDollarPrice();
            default:
                try {
                    throw new NoSuchCurrencyException(currnecy);
                } catch (NoSuchCurrencyException e) {
                    logger.error("Podano niepoprawną nazwę waluty", e);
                }
                return 1.0;
        }
    }

    private static double getDollarPrice() {
        if(dollarPrice != 0.0) {
            return dollarPrice;
        }

        URL url;
        InputStreamReader reader = null;

        try {
            url = new URL(PRICE_USD_URL);
            reader = new InputStreamReader(url.openStream());

        } catch (MalformedURLException e) {
            logger.error("Zły format URL", e);
        } catch (IOException e) {
            logger.error("Błąd podczas pobierania danych o walucie", e);
        }

        BufferedReader bufferedReader = new BufferedReader(reader);

        String response = bufferedReader.lines().collect(Collectors.joining());
        Gson g = new Gson();

        CurrencyPrice currencyPrice = g.fromJson(response, CurrencyPrice.class);

        dollarPrice = currencyPrice.getPrice();

        return dollarPrice;
    }


}
