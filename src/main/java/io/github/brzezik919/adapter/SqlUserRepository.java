package io.github.brzezik919.adapter;

import io.github.brzezik919.model.User;
import io.github.brzezik919.model.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SqlUserRepository extends UserRepository, JpaRepository<User, Integer> {
}
