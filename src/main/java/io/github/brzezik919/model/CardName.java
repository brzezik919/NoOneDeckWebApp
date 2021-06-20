package io.github.brzezik919.model;

import javax.persistence.*;

@Entity
@Table(name = "cardnames")
public class CardName {
    @Id
    private int id;

    private String name;

    private String cardType;

    public CardName(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
