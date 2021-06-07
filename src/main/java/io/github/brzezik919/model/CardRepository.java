package io.github.brzezik919.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CardRepository {
    Card save(Card entity);
    Card deleteById(int id);
    Card findById(int id);
    Page<Card> findByUser_Login_OrderByCardName_Name_Asc(String login, Pageable pageable);
    Page<Card> findByCardName_NameAndUser_LoginOrderByCardName_Name_Asc(String name, String login, Pageable pageable);
    Page<Card> findByCardName_NameAndStateOrderByCardName_Name_Asc (String name, String state, Pageable pageable);
    Page<Card> findByStateOrderByCardName_Name_Asc(String state, Pageable pageable);
    Page<Card> findByUser_Team_IdOrderByCardName_Name_Asc(int team, Pageable pageable);
    Page<Card> findByStateAndUser_IdOrderByCardName_Name_Asc(String state, int id, Pageable pageable);
    List<Card> findByUser_IdAndState(int id, String state);
    Page<Card> findByCardName_NameAndUser_Team_IdOrderByCardName_Name_Asc(String name, int team, Pageable pageable);
}