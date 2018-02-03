package pl.edu.wat.exceptions;

public class ThemeNotSupportedException extends RuntimeException {
    public ThemeNotSupportedException(String theme) {
        super("Ten motyw nie jest wspierany: " + theme);
    }
}
