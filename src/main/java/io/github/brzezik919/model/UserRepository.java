package io.github.brzezik919.model;

import java.util.List;

public interface UserRepository {
    List<User> findByLoginAndPassword(String login, String password);
    List<User> findById(int id);
    List<User> findByLogin(String login);
    List<User> findByTeam_Id(int id);
}
