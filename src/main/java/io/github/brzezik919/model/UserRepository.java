package io.github.brzezik919.model;

import java.util.List;

public interface UserRepository {
    List<User> findByIdTeam(int idTeam);
}
