package com.infra.dropwizard.auth;

import com.google.common.base.Optional;
import com.infra.dropwizard.core.User;
import com.infra.dropwizard.db.UserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * Created by mileslux on 2/24/15.
 */
public class BasicAuthenticator implements Authenticator<BasicCredentials, Long> {
    private UserDAO userDao;

    public BasicAuthenticator(UserDAO userDao) {this.userDao = userDao;}

    @Override
    public Optional<Long> authenticate(BasicCredentials credentials) throws AuthenticationException
    {
        Optional<User> existingUser = userDao.findUserByUsernameAndPassword(
                credentials.getUsername(),
                credentials.getPassword());

        if (existingUser.isPresent())
            return Optional.of(existingUser.get().getId());

        return Optional.absent();
    }
}
