package com.stedi.randomimagegenerator;

public class NotGeneratedException extends RuntimeException {
    public NotGeneratedException(String message) {
        super(message);
    }
}
