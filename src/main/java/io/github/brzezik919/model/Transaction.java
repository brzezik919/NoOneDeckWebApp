package io.github.brzezik919.model;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_owner")
    private User ownerCard;

    @ManyToOne
    @JoinColumn(name = "id_offer_owner")
    private User ownerOffer;

    @ManyToOne
    @JoinColumn(name = "id_card")
    private Card card;

    private String state;

    private String description;

    public Transaction(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getOwnerCard() {
        return ownerCard;
    }

    public void setOwnerCard(User ownerCard) {
        this.ownerCard = ownerCard;
    }

    public User getOwnerOffer() {
        return ownerOffer;
    }

    public void setOwnerOffer(User ownerOffer) {
        this.ownerOffer = ownerOffer;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
