package io.github.brzezik919.adapter;

import io.github.brzezik919.model.Transaction;
import io.github.brzezik919.model.TransactionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SqlTransactionRepository extends TransactionRepository, JpaRepository<Transaction, Integer> {
}
