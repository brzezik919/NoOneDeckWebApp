package io.github.brzezik919.model;

import java.util.List;

public interface TransactionMessageRepository {
    List<TransactionMessage> findByTransaction_IdOrderByTime(int id);
    TransactionMessage save (TransactionMessage entity);
}
