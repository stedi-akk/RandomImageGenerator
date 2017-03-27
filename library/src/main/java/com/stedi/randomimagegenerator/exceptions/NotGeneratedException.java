package com.stedi.randomimagegenerator.exceptions;

/**
 * Thrown when image is not generated.
 */
public class NotGeneratedException extends RuntimeException {
    public NotGeneratedException(String message) {
        super(message);
    }
}
