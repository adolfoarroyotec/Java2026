package com.tecmx.ordermanagement.exception;

/**
 * Thrown when there is a validation error in the input data.
 *
 * TODO: Make this class extend OrderManagementException. Use the errorCode
 * "VALIDATION_ERROR".
 */
public class ValidationException extends RuntimeException {

    // TODO: Add a 'fieldName' field to indicate which field failed validation.
    public ValidationException(String message) {
        super(message);
        // TODO: Call the OrderManagementException constructor with the correct errorCode.
    }

    // TODO: Add a constructor that takes (String message, String fieldName).
    // TODO: Add a getter for fieldName.
}
