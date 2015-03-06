package com.infra.dropwizard;

import com.infra.dropwizard.auth.BasicAuthenticator;
import com.infra.dropwizard.auth.OAuth2Authenticator;
import com.infra.dropwizard.db.AccessTokenDAO;
import com.infra.dropwizard.db.UserDAO;
import com.infra.dropwizard.resources.OAuth2Resource;
import com.infra.dropwizard.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.ChainedAuthFactory;
import io.dropwizard.auth.basic.BasicAuthFactory;
import io.dropwizard.auth.oauth.OAuthFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.infra.dropwizard.resources.SecuredResource;
import com.infra.dropwizard.health.TemplateHealthCheck;

/**
 * Created by mileslux on 2/19/15.
 */
public class TestProjectApplication extends Application<TestProjectConfiguration> {
    public static void main(String[] args) throws Exception {
        new TestProjectApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<TestProjectConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(TestProjectConfiguration configuration,
                    Environment environment) {
        final UserDAO userDao = new UserDAO();
        final AccessTokenDAO accessTokenDAO = new AccessTokenDAO();

        final UserResource userResource = new UserResource(userDao);
        final OAuth2Resource oAuth2Resource = new OAuth2Resource(accessTokenDAO);
        final SecuredResource securedResource = new SecuredResource();

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());

        environment.healthChecks().register("template", healthCheck);

        environment.jersey().register(userResource);
        environment.jersey().register(oAuth2Resource);
        environment.jersey().register(securedResource);

        final ChainedAuthFactory<Long> chainedAuthFactory = new ChainedAuthFactory<Long>(
                new BasicAuthFactory<Long>(new BasicAuthenticator(userDao), "REALM", Long.class),
                new OAuthFactory<Long>(new OAuth2Authenticator(accessTokenDAO), "REALM", Long.class)
        );
        environment.jersey().register(AuthFactory.binder(chainedAuthFactory));
    }
}
