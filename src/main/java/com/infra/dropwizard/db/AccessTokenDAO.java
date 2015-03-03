package com.infra.dropwizard.db;

import com.google.common.base.Optional;
import com.infra.dropwizard.core.AccessToken;
import com.infra.dropwizard.core.User;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mileslux on 2/25/15.
 */
public class AccessTokenDAO {
    private final Map<UUID, AccessToken> accessTokenTable = new HashMap<UUID, AccessToken>();

    public Optional<AccessToken> findAccessTokenById(final UUID accessTokenId) {
        AccessToken accessToken = accessTokenTable.get(accessTokenId);
        if (accessToken == null) {
            return Optional.absent();
        }
        return Optional.of(accessToken);
    }

    public AccessToken generateNewAccessToken(final Long userId, final DateTime dateTime) {
        AccessToken accessToken = AccessToken.create()
                .withAccessTokenId(UUID.randomUUID())
                .withUserId(userId)
                .withLastAccessUTC(dateTime)
                .build();
        accessTokenTable.put(accessToken.getAccessTokenId(), accessToken);
        return accessToken;
    }

    public void setLastAccessTime(final UUID accessTokenUUID, final DateTime dateTime) {
        AccessToken accessToken = accessTokenTable.get(accessTokenUUID);
        accessToken.setLastAccessUTC(dateTime);
    }
}
