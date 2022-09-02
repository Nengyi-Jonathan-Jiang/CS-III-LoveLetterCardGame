package Logic;

import Card.Card;
import Card.CardType;
import Logic.Actions.CardSelectAction;
import Logic.Actions.PlayerSelectionAction;
import Logic.Actions.TargetSelectAction;
import Scheduler.Action;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class GameCardTypes {
    public static final CardType Spy = new CardType() {

        @Override public String getName() { return "Spy"; }
        @Override public int getValue() { return 0; }
        @Override
        public String[] getDescription() { return new String[]{
            "At the end of the round, if you",
            "are the only player in the round",
            "who played or discarded a spy,",
            "gain 1 favor token."
        }; }
    };


    public static final CardType Guard = new CardType() {

        @Override
        public Action getAction(Game game) {
            return new Action() {
                private Player selectedPlayer;
                private CardType cardType;

                @Override
                public Iterator<? extends Action> getPreActions() {
                    return List.of(new TargetSelectAction(game, game.getOtherActivePlayers(), (Player player)->{
                        selectedPlayer = player;
                    }),new CardSelectAction(
                        game,
                        GameCardTypes.getAll().stream().filter(i->!i.getName().equals("Guard")).collect(Collectors.toList()),
                        card -> cardType = card
                    )).iterator();
                }

                @Override
                public void onFinish() {
                    System.out.println(
                        "Guard selected " + selectedPlayer.getName() + ": hand was " +
                        selectedPlayer.getHand().stream().map(i -> i.getName()).collect(Collectors.toList()) +
                        ", guess was " + cardType.getName()
                    );
                    if(selectedPlayer.getHand().stream().anyMatch(c -> c.getName().equals(cardType.getName()))){
                        selectedPlayer.eliminate();
                    }
                }
            };
        }

        @Override public String getName() { return "Guard"; }
        @Override public int getValue() { return 1; }
        @Override
        public String[] getDescription() { return new String[]{
            "Choose a player and name a",
            "non-guard card. If the player",
            "has that card, they are out of",
            "the round."
        }; }
    };


    public static final CardType Priest = new CardType() {

        @Override public String getName() { return "Priest"; }
        @Override public int getValue() { return 2; }
        @Override
        public String[] getDescription() { return new String[]{
            "Choose a player and look at",
            "their hand."
        }; }
    };

    public static final CardType Baron = new CardType() {

        @Override public String getName() { return "Baron"; }
        @Override public int getValue() { return 3; }
        @Override
        public String[] getDescription() { return new String[]{
            "Choose a player and secretly",
            "compare hands with them.",
            "Whoever has the lower value",
            "is out of the round."
        }; }
    };

    public static final CardType Handmaid = new CardType() {

        @Override public String getName() { return "Handmaid"; }
        @Override public int getValue() { return 4; }
        @Override
        public String[] getDescription() { return new String[]{
            "Until the next round, other",
            "players cannot choose you",
            "for their effects."
        }; }
    };

    public static final CardType Prince = new CardType() {

        @Override public String getName() { return "Prince"; }
        @Override public int getValue() { return 5; }
        @Override
        public String[] getDescription() { return new String[]{
            "Choose any player (including",
            "yourself). That player discards",
            "their hand and redraws."
        }; }
    };

    public static final CardType Chancellor = new CardType() {

        @Override public String getName() { return "Chancellor"; }
        @Override public int getValue() { return 6; }
        @Override
        public String[] getDescription() { return new String[]{
            "Draw 2 cards. Keep 1 card and",
            "put your other 2 on the bottom",
            "of the deck in any order."
        }; }
    };

    public static final CardType King = new CardType() {

        @Override public String getName() { return "King"; }
        @Override public int getValue() { return 7; }
        @Override
        public String[] getDescription() { return new String[]{
            "Choose another player and trade",
            "hands with them."
        }; }
    };

    public static final CardType Countess = new CardType() {

        @Override public String getName() { return "Countess"; }
        @Override public int getValue() { return 8; }
        @Override
        public String[] getDescription() { return new String[]{
            "If the King or Prince is in your",
            "hand, you must play this card."
        }; }
    };


    public static final CardType Princess = new CardType() {

        @Override public String getName() { return "Princess"; }
        @Override public int getValue() { return 9; }
        @Override
        public String[] getDescription() { return new String[]{
            "If you play or discard this card,",
            "you are out of the round."
        }; }
    };


    public static List<CardType> getAll(){
        return List.of(Spy, Guard, Priest, Baron, Handmaid, Prince, Chancellor, King, Countess, Princess);
    }
}