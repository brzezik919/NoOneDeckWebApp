package io.github.brzezik919.model;

import java.util.List;
import java.util.Set;

public interface UserRepository {
    List<User> findByLoginAndPassword(String login, String password);
}
