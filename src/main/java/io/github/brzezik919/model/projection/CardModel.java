package io.github.brzezik919.model.projection;

import io.github.brzezik919.model.Card;

import javax.validation.constraints.NotBlank;

public class CardModel {
    @NotBlank(message = "Card names's must be not empty")
    private String cardName;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Card newCard(){
        var result= new Card();
        result.setIdName(Integer.parseInt(cardName));
        result.setIdUser(1);
        result.setState("Wolne");
        return result;
    }
}