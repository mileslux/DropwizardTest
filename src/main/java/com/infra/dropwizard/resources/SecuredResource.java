package com.infra.dropwizard.resources;

import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;
import com.infra.dropwizard.core.User;
import com.infra.dropwizard.db.AccessTokenDAO;
import io.dropwizard.auth.Auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
/**
 * Created by mileslux on 2/19/15.
 */
@Path("/secured")
@Produces(MediaType.APPLICATION_JSON)
public class SecuredResource {
    @GET
    public String sayWoof(@Auth Long userId) {
        return String.format("Authenticated woof %d", userId);
    }

}
