package Logic;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private List<Player> players;
    private int numPlayers;
    private int currentPlayer;
    private List<String> cardTypes;

    public final int numcards = 21;

    private List<String> turnLog = new ArrayList<>();

    private enum States {
        INITIAL, MAIN, END
    }

    States currState;

    public Game(){
        currState = States.INITIAL;
    }

    public void initializePlayers(List<String> playerNames){
        if(currState != States.INITIAL) throw new Error("Hey that shouldn't happen here oops lmao");

        players = playerNames.stream().map(i->new Player(i, this)).collect(Collectors.toList());
        numPlayers = players.size();
        currentPlayer = 0;

        currState = States.MAIN;
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayer);
    }

    public List<Player> getPlayers(){return players;}
    public List<Player> getOtherPlayers(){
        return players
                .stream()
                .filter(i->i != getCurrentPlayer())
                .collect(Collectors.toList());
    }

    public int getCurrentPosition(){
        return currentPlayer;
    }

    public void nextPlayer(){
        do {
            currentPlayer++;
            currentPlayer %= numPlayers;
        } while(getCurrentPlayer().isOut());
    }

    public int getNumPlayers(){
        return numPlayers;
    }
}