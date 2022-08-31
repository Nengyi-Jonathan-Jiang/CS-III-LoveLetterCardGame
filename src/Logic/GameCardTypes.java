package Logic;

import Card.CardType;
import Scheduler.Action;

public class GameCardTypes {
    public static final CardType Spy = new CardType() {

        @Override protected String getName() { return "Spy"; }
        @Override protected int getValue() { return 0; }
        @Override
        protected String[] getDescription() { return new String[]{
            "At the end of the round, if you",
            "are the only player in the round",
            "who played or discarded a spy,",
            "gain 1 favor token."
        }; }
    };


    public static final CardType Guard = new CardType() {

        @Override protected String getName() { return "Guard"; }
        @Override protected int getValue() { return 1; }
        @Override
        protected String[] getDescription() { return new String[]{
            "Choose a player and name a",
            "non-guard card. If the player",
            "has that card, they are out of",
            "the round."
        }; }
    };


    public static final CardType Priest = new CardType() {

        @Override protected String getName() { return "Priest"; }
        @Override protected int getValue() { return 2; }
        @Override
        protected String[] getDescription() { return new String[]{
            "Choose a player and look at",
            "their hand."
        }; }
    };

    public static final CardType Baron = new CardType() {

        @Override protected String getName() { return "Priest"; }
        @Override protected int getValue() { return 3; }
        @Override
        protected String[] getDescription() { return new String[]{
            "Choose a player and secretly",
            "compare hands with them.",
            "Whoever has the lower value",
            "is out of the round."
        }; }
    };

    public static final CardType Handmaid = new CardType() {

        @Override protected String getName() { return "Handmaid"; }
        @Override protected int getValue() { return 4; }
        @Override
        protected String[] getDescription() { return new String[]{
            "Until the next round, other",
            "players cannot choose you",
            "for their effects."
        }; }
    };

    public static final CardType Prince = new CardType() {

        @Override protected String getName() { return "Prince"; }
        @Override protected int getValue() { return 5; }
        @Override
        protected String[] getDescription() { return new String[]{
            "Choose any player (including",
            "yourself). That player discards",
            "their hand and redraws."
        }; }
    };

    public static final CardType Chancellor = new CardType() {

        @Override protected String getName() { return "Chancellor"; }
        @Override protected int getValue() { return 6; }
        @Override
        protected String[] getDescription() { return new String[]{
            "Draw 2 cards. Keep 1 card and",
            "put your other 2 on the bottom",
            "of the deck in any order."
        }; }
    };

    public static final CardType King = new CardType() {

        @Override protected String getName() { return "King"; }
        @Override protected int getValue() { return 7; }
        @Override
        protected String[] getDescription() { return new String[]{
            "Choose another player and trade",
            "hands with them."
        }; }
    };

    public static final CardType Countess = new CardType() {

        @Override protected String getName() { return "Countess"; }
        @Override protected int getValue() { return 8; }
        @Override
        protected String[] getDescription() { return new String[]{
            "If the King or Prince is in your",
            "hand, you must play this card."
        }; }
    };


    public static final CardType Princess = new CardType() {

        @Override protected String getName() { return "Princess"; }
        @Override protected int getValue() { return 9; }
        @Override
        protected String[] getDescription() { return new String[]{
            "If you play or discard this card,",
            "you are out of the round."
        }; }
    };
}