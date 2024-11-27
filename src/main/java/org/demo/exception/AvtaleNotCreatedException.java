package org.demo.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class AvtaleNotCreatedException extends WebApplicationException {
    public AvtaleNotCreatedException(String message) {
        super(Response.status(Response.Status.EXPECTATION_FAILED).entity(message).type("text/plain").build());
    }
}
