package io.github.brzezik919.model;

import io.github.brzezik919.model.CardName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardNameRepository extends JpaRepository<CardName, Integer> {
}
