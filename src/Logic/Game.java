package Logic;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Game {
    public final static Color FG_COLOR = new Color(255, 215, 0);
    public final static Color BG_COLOR = new Color(127, 107, 0);
    
    private List<Player> players;
    private int numPlayers;
    private int currentPlayer;
    private final Deque<Card> deck = new ArrayDeque<>();

    public final int numCards = 21;

    private final List<String> turnLog = new ArrayList<>();
    
    private int startingPlayer = 0;

    public void initializePlayers(List<String> playerNames){
        players = IntStream.range(0, playerNames.size()).mapToObj(i->new Player(playerNames.get(i), this, i)).collect(Collectors.toList());
        numPlayers = players.size();
        
        startingPlayer = (int)(Math.random() * players.size());
        nextRound();
    }
    
    public void nextRound(){
        System.out.println("Started next round");

        players.forEach(Player::startRound);

        deck.clear();
        
        List<Card> cardOrder = new ArrayList<>(List.of(
            GameCardTypes.Princess,
            GameCardTypes.Countess,
            GameCardTypes.King,
            GameCardTypes.Chancellor, GameCardTypes.Chancellor,
            GameCardTypes.Prince, GameCardTypes.Prince,
            GameCardTypes.Handmaid, GameCardTypes.Handmaid,
            GameCardTypes.Baron, GameCardTypes.Baron,
            GameCardTypes.Priest, GameCardTypes.Priest,
            GameCardTypes.Guard, GameCardTypes.Guard, GameCardTypes.Guard, GameCardTypes.Guard, GameCardTypes.Guard, GameCardTypes.Guard,
            GameCardTypes.Spy, GameCardTypes.Spy
        ));
        Collections.shuffle(cardOrder);
        
        for (Card c : cardOrder) deck.addLast(c);
        
        players.forEach(this::drawCard);
    
        currentPlayer = startingPlayer;
    }

    public void setStartingPlayer(int p){
        startingPlayer = p;
    }
    
    public void returnCardToDeck(Card c) {
        deck.addLast(c);
    }

    public boolean isDeckEmpty() {
        return deck.size() <= 1;
    }
    
    public int getDeckSize(){
        return deck.size();
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