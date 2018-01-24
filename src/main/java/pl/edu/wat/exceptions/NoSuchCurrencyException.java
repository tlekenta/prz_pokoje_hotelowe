package pl.edu.wat.exceptions;

public class NoSuchCurrencyException extends Exception {
    public NoSuchCurrencyException(String currency) {
        super("Nie ma takiej waluty: " + currency);
    }
}
