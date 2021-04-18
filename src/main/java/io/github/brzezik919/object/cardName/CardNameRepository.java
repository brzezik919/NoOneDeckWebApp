package io.github.brzezik919.object.cardName;

import io.github.brzezik919.object.cardName.CardName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardNameRepository extends JpaRepository<CardName, Integer> {
}
