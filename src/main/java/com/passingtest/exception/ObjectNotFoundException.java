package com.passingtest.exception;

import java.math.BigInteger;

import static java.lang.String.format;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(BigInteger id, Class classParam) {
        super(format("Could not find %s %d", classParam.getSimpleName(), id));
    }
}
