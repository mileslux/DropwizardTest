package com.infra.dropwizard.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import com.fluentinterface.builder.Builder;
import com.fluentinterface.ReflectionBuilder;

/**
 * Created by mileslux on 2/25/15.
 */
public class AccessToken {

    public static AccessTokenBuider create() {
        return ReflectionBuilder.implementationFor(AccessTokenBuider.class).create();
    }

    public interface AccessTokenBuider extends Builder<AccessToken> {
        public AccessTokenBuider withAccessTokenId(UUID accessTokenId);
        public AccessTokenBuider withUserId(Long userId);
        public AccessTokenBuider withLastAccessUTC(DateTime lastAccessUTC);
    }

    @JsonProperty("access_token_id")
    @NotNull
    private UUID accessTokenId;

    @JsonProperty("user_id")
    @NotNull
    private Long userId;

    @JsonProperty("last_access_utc")
    @NotNull
    private DateTime lastAccessUTC;

    public UUID getAccessTokenId() {
        return accessTokenId;
    }

    public void setAccessTokenId(UUID accessTokenId) {
        this.accessTokenId = accessTokenId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public DateTime getLastAccessUTC() {
        return lastAccessUTC;
    }

    public void setLastAccessUTC(DateTime lastAccessUTC) {
        this.lastAccessUTC = lastAccessUTC;
    }
}
