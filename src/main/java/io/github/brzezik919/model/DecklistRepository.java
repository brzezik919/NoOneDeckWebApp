package io.github.brzezik919.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DecklistRepository {
    Decklist save(Decklist decklist);
    Decklist findById(int id);
    Page<Decklist> findByUser_IdOrderByNameDesc (int id, Pageable pageable);
    Page<Decklist> findByUser_Team_IdAndTeamSharedOrderByNameDesc(int id, boolean teamShared, Pageable pageable);
    Page<Decklist> findByPublicSharedOrderByNameDesc(boolean publicShared, Pageable pageable);

}
