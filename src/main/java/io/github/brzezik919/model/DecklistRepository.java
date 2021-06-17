package io.github.brzezik919.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DecklistRepository {
    Decklist save(Decklist decklist);
    Page<Decklist> findByUser_IdOrderByNameDesc (int id, Pageable pageable);
}
