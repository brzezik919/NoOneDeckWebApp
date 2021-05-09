package io.github.brzezik919.model;

import java.util.List;

public interface TeamRepository {
    Team findById(int id);
    Team findByCode(String code);
    Team save (Team entity);
}
