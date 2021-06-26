package io.github.brzezik919.model;

public enum StateDecklist {
    PRIVATE("private"),
    PUBLIC ("public"),
    TEAM ("team");

    private final String state;

    StateDecklist(String value){
        state = value;
    }

    public String toString(){
        return this.state;
    }
}
