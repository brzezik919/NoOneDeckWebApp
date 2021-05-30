package io.github.brzezik919.model;

public interface TeamRepository {
    Team findByCode(String code);
    Team findByName (String name);
    Team findById (int id);
    Team save (Team team);
    Team deleteById(int id);
}