package pl.edu.wat.exceptions;

public class LanguageNotSupportedException extends RuntimeException {
    public LanguageNotSupportedException(String language) {
        super("Ten język nie jest wspierany: " + language);
    }
}
