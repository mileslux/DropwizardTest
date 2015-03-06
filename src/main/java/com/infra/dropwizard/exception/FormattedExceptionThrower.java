package com.infra.dropwizard.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by mileslux on 3/6/15.
 */
public final class FormattedExceptionThrower {
    private FormattedExceptionThrower() { }

    public static void throwWebApplicationException(final Response.Status status, final String message) {
        throw new WebApplicationException(Response.status(status).entity("{\"error\":\"" + message + "\"}").build());
    }
}
