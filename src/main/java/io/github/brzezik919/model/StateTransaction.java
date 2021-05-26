package io.github.brzezik919.model;

public enum StateTransaction {
    pending ("Pending"),
    accepted ("Accepted"),
    canceled ("Canceled");

    private final String state;

    StateTransaction(String value){
        state = value;
    }

    public String toString(){
        return this.state;
    }
}
