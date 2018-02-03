package pl.edu.wat.exceptions;

public class NoSuchCurrencyException extends RuntimeException {
    public NoSuchCurrencyException(String currency) {
        super("Nie ma takiej waluty: " + currency);
    }
}
