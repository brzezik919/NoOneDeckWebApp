package io.github.brzezik919.model;

public interface TeamRepository {
    Team findByCode(String code);
    Team save (Team team);
}
