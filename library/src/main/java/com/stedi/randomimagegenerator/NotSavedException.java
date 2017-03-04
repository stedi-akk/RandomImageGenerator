package com.stedi.randomimagegenerator;

import java.io.IOException;

public class NotSavedException extends IOException {
    public NotSavedException(String message) {
        super(message);
    }
}
