package com.infra.dropwizard.core;

import com.fluentinterface.ReflectionBuilder;
import com.fluentinterface.builder.Builder;

import javax.validation.constraints.NotNull;

/**
 * Created by mileslux on 2/25/15.
 */
public class SignupCredentials {

    public static SignupCredentialsBuilder create() {
        return ReflectionBuilder.implementationFor(SignupCredentialsBuilder.class).create();
    }

    public interface SignupCredentialsBuilder extends Builder<SignupCredentials> {
        public SignupCredentialsBuilder withUsername(String username);
        public SignupCredentialsBuilder withPassword(String password);
    }

    @NotNull
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
