package io.github.brzezik919.model;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    List<User> findById(int id);
    User findByLogin(String login);
    User findByNickname(String nickname);
    List<User> findByTeam_IdAndStatus(int id, boolean status);
    User save(User user);
}
