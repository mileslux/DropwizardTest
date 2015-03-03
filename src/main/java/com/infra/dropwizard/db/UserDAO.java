package com.infra.dropwizard.db;

import com.infra.dropwizard.core.User;

import java.util.HashMap;
import java.util.Map;
import com.google.common.base.Optional;

/**
 * Created by mileslux on 2/24/15.
 */
public class UserDAO {

    private final Map<Long, User> userTable = new HashMap<Long, User>();
    private long userCounter = 0;

    public Optional<User> createUserByUsernameAndPassword(final String username, final String password) {
        Optional<User> existingUser = findUserByUsernameAndPassword(username, password);
        if (existingUser.isPresent()) return Optional.absent();
        User newUser = User.create()
                .withId(userCounter)
                .withUsername(username)
                .withPassword(password)
                .build();
        userTable.put(userCounter, newUser);
        userCounter++;
        return Optional.of(newUser);
    }

    public Optional<User> findUserByUsernameAndPassword(final String username, final String password) {
        for (Map.Entry<Long, User> entry : userTable.entrySet()) {
            User user = entry.getValue();
            if (user.getPassword().equals(password) && user.getUsername().equals(username)) {
                return Optional.of(user);
            }
        }
        return Optional.absent();
    }

    public boolean deleteUserByUsernameAndPassword(final String username, final String password) {
        Optional<User> existingUser = findUserByUsernameAndPassword(username, password);
        if (!existingUser.isPresent()) return false;
        userTable.remove(existingUser.get().getId());
        return true;
    }
}
