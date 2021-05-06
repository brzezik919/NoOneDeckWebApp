package io.github.brzezik919.adapter;

import io.github.brzezik919.model.Team;
import io.github.brzezik919.model.TeamRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SqlTeamRepository extends TeamRepository, JpaRepository<Team, Integer> {
}
