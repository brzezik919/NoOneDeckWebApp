package io.github.brzezik919.object.card;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CardRepository {
    List<Card> findAll();
    Optional<Card> findById(Integer id);
    Card save(Card entity);
    List<Card> findByIdUser(int idUser);
}
