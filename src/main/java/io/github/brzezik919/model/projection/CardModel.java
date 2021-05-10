package io.github.brzezik919.model.projection;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.CardName;
import io.github.brzezik919.model.User;

public class CardModel {
    private String cardName;

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
        result.setState("Wolne");
        return result;
    }
}