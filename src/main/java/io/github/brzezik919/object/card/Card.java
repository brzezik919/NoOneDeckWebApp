package io.github.brzezik919.object.card;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Password's must be not empty")
    private int idUser;

    @NotBlank(message = "Password's must be not empty")
    private int idName;

    @NotBlank(message = "Password's must be not empty")
    private String state;

    public Card(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdName() {
        return idName;
    }

    public void setIdName(int idName) {
        this.idName = idName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
