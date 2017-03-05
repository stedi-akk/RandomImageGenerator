package com.stedi.randomimagegenerator.exceptions;

import java.io.IOException;

public class NotSavedException extends IOException {
    public NotSavedException(String message) {
        super(message);
    }
}
