package com.infra.dropwizard.resources;

import com.google.common.base.Optional;
import com.infra.dropwizard.core.SignupCredentials;
import com.infra.dropwizard.core.User;
import com.infra.dropwizard.db.UserDAO;
import com.infra.dropwizard.exception.FormattedExceptionThrower;

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
        Optional<User> existingUser = userDao.findUserByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            FormattedExceptionThrower.throwWebApplicationException(Response.Status.CONFLICT, "User with the specified login already exists");
        }

        userDao.createUserByUsernameAndPassword(user.getUsername(), user.getPassword());

        return Response.status(Response.Status.CREATED).build();
    }
}
