package org.demo.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExceptionHandler implements ExceptionMapper<AvtaleNotCreatedException> {
    @Override
    public Response toResponse(AvtaleNotCreatedException exception) {
        int statusCode = exception.getResponse().getStatus();
        String errorMessage = exception.getMessage();

        // Log the exception for internal debugging

        return Response.status(statusCode)
                .entity(errorMessage)
                .build();
    }
}
