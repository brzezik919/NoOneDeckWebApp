package io.github.brzezik919.model;

import java.util.List;

public interface TransactionRepository {
    Transaction save (Transaction entity);
    List<Transaction> findByOwnerCard_IdAndState(int id, String state);
    List<Transaction> findByOwnerCard_IdAndStateOrOwnerOffer_IdAndState (int id, String state, int id2, String state2);
    Transaction findById(int id);
}
