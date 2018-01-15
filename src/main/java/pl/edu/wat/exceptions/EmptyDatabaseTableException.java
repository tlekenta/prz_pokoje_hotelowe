package pl.edu.wat.exceptions;

public class EmptyDatabaseTableException extends Exception {
    public EmptyDatabaseTableException(String tableName) {
        super("Tabela " + tableName + " jest pusta.");
    }
}
