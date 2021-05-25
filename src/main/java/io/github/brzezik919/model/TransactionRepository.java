package io.github.brzezik919.model;

import java.util.List;

public interface TransactionRepository {
    Transaction save (Transaction entity);
    List<Transaction> findByOwnerCard_IdAndState(int id, String state);
    Transaction findById(int id);
}
