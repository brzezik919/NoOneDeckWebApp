package io.github.brzezik919.adapter;

import io.github.brzezik919.model.Decklist;
import io.github.brzezik919.model.DecklistRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SqlDecklistRepository extends DecklistRepository, JpaRepository<Decklist, Integer> {
}
