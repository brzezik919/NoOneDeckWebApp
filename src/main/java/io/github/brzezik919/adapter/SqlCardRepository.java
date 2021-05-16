package io.github.brzezik919.adapter;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.CardRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SqlCardRepository extends CardRepository, JpaRepository<Card, Integer> {
}