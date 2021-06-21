package io.github.brzezik919.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DecklistRepository {
    Decklist save(Decklist decklist);
    Decklist deleteById(int id);
    Decklist findById(int id);
    Page<Decklist> findByUser_IdOrderByNameAsc(int id, Pageable pageable);
    Page<Decklist> findByUser_Team_IdAndTeamSharedOrderByNameAsc(int id, boolean teamShared, Pageable pageable);
    Page<Decklist> findByPublicSharedOrderByNameAsc(boolean publicShared, Pageable pageable);

}
