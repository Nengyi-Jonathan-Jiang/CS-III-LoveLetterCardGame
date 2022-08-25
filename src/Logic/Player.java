package Logic;

import Card.Card;

public class Player {
    private Card hand;
    private final String name;

    public Player(String name){
        this.name = name;
    }

    public String getName(){return name;}

    public boolean equals(Object o){
        return o instanceof Player && ((Player)o).getName().equals(name);
    }
}