package br.com.codart.domain.exceptions;

public class LimitExceededException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Limit of %d items per operation exceeded.";

    public LimitExceededException(int limit) {
        super(String.format(DEFAULT_MESSAGE, limit));
    }
}
