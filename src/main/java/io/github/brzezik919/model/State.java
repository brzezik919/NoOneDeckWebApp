package io.github.brzezik919.model;

public enum State {
    free ("Free"),
    bought ("Bought"),
    forSell ("For Sale"),
    inUse ("In Use"),
    pending("Pending");

    private final String state;

    State(String value){
        state = value;
    }

    public String toString(){
        return this.state;
    }

}
