package io.github.brzezik919.object.cardName;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "cardnames")
public class CardName {
    @Id
    private int id;

    @NotBlank(message = "Password's must be not empty")
    private String name;

    public CardName(){

    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
