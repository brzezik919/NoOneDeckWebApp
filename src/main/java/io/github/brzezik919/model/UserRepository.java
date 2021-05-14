package io.github.brzezik919.model;

import java.util.List;

public interface UserRepository {
    List<User> findById(int id);
    User findByLogin(String login);
    List<User> findByTeam_Id(int id);
    User save(User user);
}
