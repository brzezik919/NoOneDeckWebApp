package io.github.brzezik919.model;


public interface TeamRepository {
    Team findById(int id);
    Team findByCode(String code);
    Team save (Team team);
}
