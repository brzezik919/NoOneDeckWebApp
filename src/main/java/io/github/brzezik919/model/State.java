package io.github.brzezik919.model;

public enum State {
    free ("Wolne"),
    bought ("Bought"),
    forSell ("For Sell"),
    inUse ("In Use");

    private String state;

    State(String value){
        state = value;
    }

    public String toString(){
        return this.state;
    }

}
