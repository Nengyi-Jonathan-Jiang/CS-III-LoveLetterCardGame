package Logic;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    private List<Player> players;
    private int numPlayers;
    private int currentPlayer;
    private final Deque<Card> deck = new ArrayDeque<>();

    public final int numCards = 21;

    private List<String> turnLog = new ArrayList<>();

    public void initializePlayers(List<String> playerNames){
        players = IntStream.range(0, playerNames.size()).mapToObj(i->new Player(playerNames.get(i), this, i)).collect(Collectors.toList());
        numPlayers = players.size();
        currentPlayer = 0;

        for (Card c : new Card[]{
            GameCardTypes.Baron,
            GameCardTypes.Princess,
            GameCardTypes.Spy,
            GameCardTypes.Chancellor,
            GameCardTypes.Guard,
            GameCardTypes.King,
            GameCardTypes.Prince,
            GameCardTypes.Handmaid,
            GameCardTypes.Countess,
            GameCardTypes.Priest,
            GameCardTypes.Guard,
            GameCardTypes.Guard,
            GameCardTypes.Prince,
            GameCardTypes.Guard,
            GameCardTypes.Priest,
            GameCardTypes.Handmaid,
            GameCardTypes.Guard,
            GameCardTypes.Spy,
            GameCardTypes.Baron,
            GameCardTypes.Chancellor,
            GameCardTypes.Guard,
        }) deck.addLast(c);

        players.forEach(this::draw);
    }

    public void returnCard(Card c){
        deck.addLast(c);
    }

    public boolean deckEmpty(){
        return deck.size() <= 1;
    }

    public void draw(@NotNull Player player){
        draw(player, 1);
    }
    public void draw(@NotNull Player player, int num){
        while(num --> 0) {
            player.addToHand(deck.peek());
            deck.pop();
        }
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayer);
    }

    public List<Player> getAllPlayers() {
        return players;
    }

    public List<Player> getActivePlayers() {
        return players.stream().filter(player->!player.isEliminated()).collect(Collectors.toList());
    }

    public List<Player> getOtherActivePlayers(){
        return players.stream()
                .filter(player->!player.isEliminated())
                .filter(i->i != getCurrentPlayer())
                .collect(Collectors.toList());
    }

    public int getCurrentPosition(){
        return currentPlayer;
    }

    public void moveToNextPlayer(){
        do {
            currentPlayer++;
            currentPlayer %= numPlayers;
        } while(getCurrentPlayer().isEliminated());
    }

    public int getNumPlayers(){
        return numPlayers;
    }
}