package com.infra.dropwizard.core;

import com.fluentinterface.builder.Builder;
import com.fluentinterface.ReflectionBuilder;

import java.util.Objects;

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

    private Long id;
    private String username;
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
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other.getClass() != User.class) return false;
        final User otherUser = (User)other;
        return otherUser.getUsername().equals(username) &&
                otherUser.getPassword().equals(password) &&
                id == otherUser.getId();
    }
}
