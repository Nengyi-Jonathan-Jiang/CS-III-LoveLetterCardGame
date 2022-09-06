package Logic;

import Card.Card;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    private List<Player> players;
    private int numPlayers;
    private int currentPlayer;
    private Deque<Card> deck = new ArrayDeque<>();

    public final int numCards = 21;

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

        for (Card c : new Card[]{
            GameCardTypes.Baron.makeCard(),
            GameCardTypes.Princess.makeCard(),
            GameCardTypes.Spy.makeCard(),
            GameCardTypes.Chancellor.makeCard(),
            GameCardTypes.Guard.makeCard(),
            GameCardTypes.King.makeCard(),
            GameCardTypes.Prince.makeCard(),
            GameCardTypes.Handmaid.makeCard(),
            GameCardTypes.Countess.makeCard(),
            GameCardTypes.Priest.makeCard(),
            GameCardTypes.Guard.makeCard(),
            GameCardTypes.Guard.makeCard(),
            GameCardTypes.Prince.makeCard(),
            GameCardTypes.Guard.makeCard(),
            GameCardTypes.Priest.makeCard(),
            GameCardTypes.Handmaid.makeCard(),
            GameCardTypes.Guard.makeCard(),
            GameCardTypes.Spy.makeCard(),
            GameCardTypes.Baron.makeCard(),
            GameCardTypes.Chancellor.makeCard(),
            GameCardTypes.Guard.makeCard(),
        }) deck.addLast(c);

        currState = States.MAIN;

        players.forEach(this::draw);
    }

    public void returnCard(Card c){
        deck.addLast(c);
    }

    public boolean deckEmpty(){
        return deck.size() <= 1;
    }

    public void draw(@NotNull Player player){draw(player, 1);}
    public void draw(@NotNull Player player, int num){
        while(num --> 0) {
            player.draw(deck.peek());
            deck.pop();
        }
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

    public List<Player> getActivePlayers(){
        return players.stream().filter(player->!player.isOut()).collect(Collectors.toList());
    }

    public List<Player> getOtherActivePlayers(){
        return players.stream()
                .filter(player->!player.isOut())
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