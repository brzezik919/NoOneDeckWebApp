package io.github.brzezik919.model.projection;

import io.github.brzezik919.model.Card;
import io.github.brzezik919.model.CardName;
import io.github.brzezik919.model.User;

import javax.validation.constraints.NotBlank;

public class CardModel {
    private String cardName;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    /*public Card newCard(){
        var result = new Card();
        var name = new CardName();
        var user = new User();
        name.setName(cardName);
        result.setCardName(null);
        result.setUser(null);
        result.setState("Wolne");
        return result;
    }*/

    /*public Card newCard(CardName cardName){
        var result = new Card();
        result.setCardName(cardName);
        result.setUser(1);
        result.setState("Wolne");
        return result;
    }*/
}