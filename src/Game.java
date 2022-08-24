import java.util.List;

public class Game {
    private List<Player> players;
    private List<String> cardTypes;
    private Deck deck;

    public Game(List<Player> players){
        this.players = players;
    }

    public GameSet<Player> getAllPlayers(){return new GameSet<Player>(players);}
}
