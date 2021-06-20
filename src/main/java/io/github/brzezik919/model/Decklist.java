package io.github.brzezik919.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "decklists")
public class Decklist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    private String name;

    @Column
    @Type(type="text")
    private String deck;

    private boolean teamShared;
    private boolean publicShared;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTeamShared() {
        return teamShared;
    }

    public boolean isPublicShared() {
        return publicShared;
    }

    public void setPublicShared(boolean publicShared) {
        this.publicShared = publicShared;
    }

    public void setTeamShared(boolean teamShared) {
        this.teamShared = teamShared;
    }

    public String getDeck() {
        return deck;
    }

    public void setDeck(String deck) {
        this.deck = deck;
    }
}
