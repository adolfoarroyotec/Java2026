package com.tecmx.ordermanagement.exception;

/**
 * Thrown when a resource is not found (order, product, customer).
 *
 * TODO: Make this class extend OrderManagementException. Make sure to pass an
 * appropriate errorCode (e.g. "RESOURCE_NOT_FOUND").
 */
public class ResourceNotFoundException extends RuntimeException {

    // TODO: Add a 'resourceId' field to identify which resource was not found.
    public ResourceNotFoundException(String message) {
        super(message);
        // TODO: Call the OrderManagementException constructor with the correct errorCode.
    }

    // TODO: Add a getter for resourceId.
}
