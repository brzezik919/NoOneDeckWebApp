package io.github.brzezik919.model;

import io.github.brzezik919.model.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository {
    List<Card> findAll();
    Optional<Card> findById(Integer id);
    Card save(Card entity);
    List<Card> findByIdUser(int idUser);
    List<Card> findByIdName(String cardName);
}
