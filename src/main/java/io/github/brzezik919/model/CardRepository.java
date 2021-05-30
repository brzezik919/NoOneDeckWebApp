package io.github.brzezik919.model;

import java.util.List;

public interface CardRepository {
    Card save(Card entity);
    Card deleteById(int id);
    Card findById(int id);
    List<Card> findByUser_Login(String login);
    List<Card> findByState(String state);
    List<Card> findByUser_Team_Id(int team);
    List<Card> findByCardName_NameAndUser_Login(String name, String login);
    List<Card> findByCardName_NameAndUser_Team_Id(String name, int team);
    List<Card> findByCardName_NameAndState(String name, String state);
}