package io.github.brzezik919.model.projection;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.CardName;
import io.github.brzezik919.model.StateCard;
import io.github.brzezik919.model.User;

public class CardModel {
    private int id;
    private String state;
    private String cardName;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Card newCard(CardName cardName, User user){
        var result = new Card();
        result.setCardName(cardName);
        result.setUser(user);
        result.setState(StateCard.FREE.toString());
        return result;
    }
}