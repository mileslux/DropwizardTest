package com.infra.dropwizard.auth;

import com.google.common.base.Optional;
import com.infra.dropwizard.core.AccessToken;
import com.infra.dropwizard.db.AccessTokenDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.UUID;

/**
 * Created by mileslux on 2/26/15.
 */
public class OAuth2Authenticator implements Authenticator<String, Long> {
    public static final int ACCESS_TOKEN_EXPIRE_TIME_MIN = 30;
    private AccessTokenDAO accessTokenDAO;

    public OAuth2Authenticator(AccessTokenDAO accessTokenDAO) {
        this.accessTokenDAO = accessTokenDAO;
    }

    @Override
    public Optional<Long> authenticate(String accessTokenId) throws AuthenticationException {
// Check input, must be a valid UUID
        UUID accessTokenUUID;
        try {
            accessTokenUUID = UUID.fromString(accessTokenId);
        } catch (IllegalArgumentException e) {
            return Optional.absent();
        }
// Get the access token from the database
        Optional<AccessToken> accessToken = accessTokenDAO.findAccessTokenById(accessTokenUUID);
        if (accessToken == null || !accessToken.isPresent()) {
            return Optional.absent();
        }
// Check if the last access time is not too far in the past (the access token is expired)
        Period period = new Period(accessToken.get().getLastAccessUTC(), new DateTime());
        if (period.getMinutes() > ACCESS_TOKEN_EXPIRE_TIME_MIN) {
            return Optional.absent();
        }
// Update the access time for the token
        accessTokenDAO.setLastAccessTime(accessTokenUUID, new DateTime());
// Return the user's id for processing
        return Optional.of(accessToken.get().getUserId());
    }
}
