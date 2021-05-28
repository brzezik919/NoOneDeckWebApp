package io.github.brzezik919.model;

public enum StateTransaction {
    PENDING ("Pending"),
    ACCEPTED ("Accepted"),
    CANCELED ("Canceled");

    private final String state;

    StateTransaction(String value){
        state = value;
    }

    public String toString(){
        return this.state;
    }
}
