package com.lambda.foodtrucktrackr.exceptions;

public class ResourceFoundException extends RuntimeException {
    public ResourceFoundException(String message) {
        super("Error from a Lambda School application " + message);
    }
}
