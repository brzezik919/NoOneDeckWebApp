package io.github.brzezik919.object.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface SqlCardRepository extends CardRepository, JpaRepository<Card, Integer> {
}