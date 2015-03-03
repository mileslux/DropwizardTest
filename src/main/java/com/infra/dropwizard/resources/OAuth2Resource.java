package com.infra.dropwizard.resources;

import com.google.common.base.Optional;
import com.infra.dropwizard.core.AccessToken;
import com.infra.dropwizard.core.User;
import com.infra.dropwizard.db.AccessTokenDAO;
import com.infra.dropwizard.db.UserDAO;
import io.dropwizard.auth.Auth;
import org.joda.time.DateTime;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by mileslux on 2/25/15.
 */
@Path("/oauth2")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OAuth2Resource {
    private AccessTokenDAO accessTokenDAO;
    private UserDAO userDAO;
    public OAuth2Resource(AccessTokenDAO accessTokenDAO, UserDAO userDAO) {
        this.accessTokenDAO = accessTokenDAO;
        this.userDAO = userDAO;
    }

    @POST
    public String postForToken(@Auth Long userId, String dummyString) {
        AccessToken accessToken = accessTokenDAO.generateNewAccessToken(userId, new DateTime());
        return accessToken.getAccessTokenId().toString();
    }
}
