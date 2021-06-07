package io.github.brzezik919.adapter;

import io.github.brzezik919.model.Opinion;
import io.github.brzezik919.model.OpinionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SqlOpinionRepository extends OpinionRepository, JpaRepository<Opinion, Integer> {
}
