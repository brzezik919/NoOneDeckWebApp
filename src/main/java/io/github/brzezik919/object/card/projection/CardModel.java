package io.github.brzezik919.object.card.projection;

import io.github.brzezik919.object.card.Card;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class CardModel {
    @NotBlank(message = "Cards must be not empty")
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
