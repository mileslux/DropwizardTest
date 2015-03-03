package com.infra.dropwizard.resources;

import com.google.common.base.Optional;
import com.infra.dropwizard.core.SignupCredentials;
import com.infra.dropwizard.core.User;
import com.infra.dropwizard.db.UserDAO;
import io.dropwizard.auth.Auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by mileslux on 2/24/15.
 */
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    private UserDAO userDao;

    public UserResource(UserDAO userDao) {
        this.userDao = userDao;
    }

    @POST
    public Response post(SignupCredentials user) {
        Optional<User> newUser = userDao.createUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (!newUser.isPresent()) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + "User with the specified login already exists" + "\"}").build());
        }

        return Response.status(Response.Status.CREATED).build();
    }
}
