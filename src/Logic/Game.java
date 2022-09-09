package Logic;

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
        
        nextRound();
    }
    
    public void nextRound(){

        System.out.println("Started next round");

        players.forEach(Player::startRound);

        deck.clear();
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
        
        players.forEach(this::drawCard);
    
        currentPlayer = 0;
    }

    public void returnCardToDeck(Card c) {
        deck.addLast(c);
    }

    public boolean isDeckEmpty() {
        return deck.size() <= 1;
    }

    public void drawCard(Player player) {
        drawCards(player, 1);
    }
    public void drawCards(Player player, int num) {
        while(num --> 0) {
            player.addToHand(deck.peek());
            deck.pop();
        }
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public List<Player> getAllPlayers() {
        return players;
    }

    public List<Player> getActivePlayers() {
        return players.stream().filter(player -> !player.isEliminated()).collect(Collectors.toList());
    }

    public List<Player> getOtherActivePlayers() {
        return players.stream()
                .filter(player -> !player.isEliminated())
                .filter(i -> i != getCurrentPlayer())
                .collect(Collectors.toList());
    }

    public int getCurrentIndex() {
        return currentPlayer;
    }

    public void moveToNextPlayer(){
        do {
            currentPlayer++;
            currentPlayer %= numPlayers;
        } while(getCurrentPlayer().isEliminated());
    }

    public int getNumPlayers() {
        return numPlayers;
    }
    
    public void log(String s){
        turnLog.add(s);
        System.out.println(s);
    }
}