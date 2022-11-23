package com.passingtest.exception;

import static java.lang.String.format;

public class ObjectNotFoundException extends Exception {

    public ObjectNotFoundException(int id, Class classParam) {
        super(format("Could not find %s %d", classParam.getSimpleName(), id));
    }
}
