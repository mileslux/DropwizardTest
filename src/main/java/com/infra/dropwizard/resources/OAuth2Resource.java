package com.infra.dropwizard.resources;

import com.infra.dropwizard.core.AccessToken;
import com.infra.dropwizard.db.AccessTokenDAO;
import io.dropwizard.auth.Auth;
import org.joda.time.DateTime;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by mileslux on 2/25/15.
 */
@Path("/oauth2")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OAuth2Resource {
    private AccessTokenDAO accessTokenDAO;

    public OAuth2Resource(AccessTokenDAO accessTokenDAO) {
        this.accessTokenDAO = accessTokenDAO;
    }

    @POST
    public String postForToken(@Auth Long userId, String dummyString) {
        AccessToken accessToken = accessTokenDAO.generateNewAccessToken(userId, new DateTime());
        return accessToken.getAccessTokenId().toString();
    }
}
