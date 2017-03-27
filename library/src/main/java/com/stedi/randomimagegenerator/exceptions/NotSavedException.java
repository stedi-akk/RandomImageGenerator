package com.stedi.randomimagegenerator.exceptions;

import java.io.IOException;

/**
 * Thrown when image is not saved.
 */
public class NotSavedException extends IOException {
    public NotSavedException(String message) {
        super(message);
    }
}
