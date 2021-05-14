package io.github.brzezik919.model;

import java.util.List;

public interface CardRepository {
    List<Card> findAll();
    Card save(Card entity);
    List<Card> findByUser_Login(String login);
    List<Card> findByCardName_Name(String name);
    List<Card> findByState(String state);
    List<Card> findByCardName_NameAndUser_Login(String name, String login);
}
