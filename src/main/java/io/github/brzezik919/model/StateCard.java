package io.github.brzezik919.model;

public enum StateCard {
    FREE ("Free"),
    BOUGHT ("Bought"),
    FORSALE("For Sale"),
    INUSE ("In Use"),
    PENDING("Pending");

    private final String state;

    StateCard(String value){
        state = value;
    }

    public String toString(){
        return this.state;
    }
}
