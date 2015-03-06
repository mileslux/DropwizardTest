package com.infra.dropwizard;

import com.infra.dropwizard.core.SignupCredentials;
import com.infra.dropwizard.utils.ClientRequestLoggingFilter;
import com.infra.dropwizard.utils.ClientResponseLoggingFilter;
import org.testng.Assert;
import org.testng.annotations.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

/**
 * Created by mileslux on 2/24/15.
 */
@Test(groups= "simple-test")
public class SimpleTest {

    private static final String username = "login";
    private static final String password = "password";

    private static final String OAUTH2_PATH = "http://localhost:8100/application/oauth2";
    private static final String USERS_PATH = "http://localhost:8100/application/users";
    private static final String SECURED_PATH = "http://localhost:8100/application/secured";

    private static final String DUMMY_STRING = "Nothing here yet";

    private static final ClientRequestLoggingFilter clientRequestLoggingFilter = new ClientRequestLoggingFilter();
    private static final ClientResponseLoggingFilter clientResponseLoggingFilter = new ClientResponseLoggingFilter();

    private Client client;

    @BeforeMethod
    public void beforeMethod() {
        client = ClientBuilder.newClient();
        client.register(clientRequestLoggingFilter);
        client.register(clientResponseLoggingFilter);
    }

    @AfterMethod
    public void afterMethod() {
        client.close();
    }

    @Test
    public void authGetTokenWithoutCredentialsReturns401() {
        Response response = client
                .target(OAUTH2_PATH)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(DUMMY_STRING, MediaType.APPLICATION_JSON));
        Assert.assertEquals(response.getStatus(), Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void authGetTokenWithWrongCredentialsReturns401() {
        HttpAuthenticationFeature httpAuthenticationFeature =
                HttpAuthenticationFeature.basic("non", "existent");

        client.register(httpAuthenticationFeature);

        Response response = client
                .target(OAUTH2_PATH)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(DUMMY_STRING, MediaType.APPLICATION_JSON));
        Assert.assertEquals(response.getStatus(), Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void authGetSecuredResourceWithoutTokenReturns401() {
        Response response = client
                .target(SECURED_PATH)
                .request(MediaType.APPLICATION_JSON)
                .get();

        Assert.assertEquals(response.getStatus(), Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    public void authSignupNewUserAndAccessSecuredResource() {

        SignupCredentials signupCredentials = SignupCredentials.create()
                .withUsername(username)
                .withPassword(password)
                .build();

        Response signupResponse = client
                .target(USERS_PATH)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(signupCredentials, MediaType.APPLICATION_JSON));

        int status = signupResponse.getStatus();
        Assert.assertTrue(status == Response.Status.CREATED.getStatusCode() ||
                status == Response.Status.CONFLICT.getStatusCode());

        HttpAuthenticationFeature httpAuthenticationFeature =
                HttpAuthenticationFeature.basic(username, password);

        client.register(httpAuthenticationFeature);

        Response authResponse = client
                .target(OAUTH2_PATH)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(DUMMY_STRING, MediaType.APPLICATION_JSON));
        Assert.assertEquals(authResponse.getStatus(), Response.Status.OK.getStatusCode());

        String accessToken = authResponse.readEntity(String.class);

        Response getAuthResourceResponse = client
                .target(SECURED_PATH)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", String.format("Bearer %s", accessToken))
                .get();

        Assert.assertEquals(getAuthResourceResponse.getStatus(), Response.Status.OK.getStatusCode());

        String secretText = getAuthResourceResponse.readEntity(String.class);
        Assert.assertTrue(secretText.contains("Authenticated woof"));
    }
}
