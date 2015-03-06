package com.infra.dropwizard.core;

import com.fluentinterface.builder.Builder;
import com.fluentinterface.ReflectionBuilder;

import javax.validation.constraints.NotNull;

/**
 * Created by mileslux on 2/24/15.
 */
public class User {

    public static UserBuilder create() {
        return ReflectionBuilder.implementationFor(UserBuilder.class).create();
    }

    public interface UserBuilder extends Builder<User> {
        public UserBuilder withUsername(String login);
        public UserBuilder withPassword(String password);
        public UserBuilder withId(Long id);
    }

    @NotNull
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;
        if (!password.equals(user.password)) return false;
        if (!username.equals(user.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }
}
