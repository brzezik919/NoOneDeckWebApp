package io.github.brzezik919.model;

import java.util.List;

public interface CardRepository {
    List<Card> findAll();
    Card save(Card entity);
    List<Card> findByIdUser(int idUser);
    List<Card> findByCardName_Name(String name);
    List<Card> findByState(String state);
}
