package io.github.brzezik919.adapter;

import io.github.brzezik919.model.TransactionMessage;
import io.github.brzezik919.model.TransactionMessageRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SqlTransactionMessageRepository extends TransactionMessageRepository, JpaRepository<TransactionMessage, Integer> {
}
