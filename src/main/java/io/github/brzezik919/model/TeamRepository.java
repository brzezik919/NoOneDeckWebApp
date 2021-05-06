package io.github.brzezik919.model;

import java.util.List;

public interface TeamRepository {
    Team findById(int id);
    Team save (Team entity);
}
